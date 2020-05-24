import { wsUrl } from "./variables";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";

let stompClient;

export const onAggregatesReceived = cb => {
  connect(cb);
};

function sendMessage() {
  stompClient.send("/messages/log", {}, "Listening");
}

function connect(cb) {
  const socket = new SockJS(wsUrl);
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    stompClient.subscribe("/topic/messages", messageOutput => {
      const aggregates = JSON.parse(messageOutput.body)
      cb(aggregates);
    });
    sendMessage();
  });
}
