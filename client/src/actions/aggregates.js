import axios from "axios";
import _ from "lodash";
import { combineReducers } from "redux";
import { apiUrl } from "../variables";

// -------------- Action types -----------
const AGGREGATES_UPDATED = "AGGREGATES_UPDATED";
const INTERVAL_FETCHED = "INTERVAL_FETCHED";

// -------------- Action creators -----------
export const updateAggregates = aggregates => ({
  type: AGGREGATES_UPDATED,
  payload: {
    ...aggregates
  }
});

export const getInterval = () => async (dispatch, getState) => {
  try {
    console.log(apiUrl)
    const res = await axios.get(`${apiUrl}/interval`);
    dispatch(fetchIntervalSuccess(res.data));
  } catch (e) {
    console.log("Error fetching intervals", e);
  }
};

export const changeInterval = (intervalInSeconds) => async (dispatch, getState) => {
  try {
    const res = await axios.post(`${apiUrl}/interval`, { intervalInSeconds });
    dispatch(fetchIntervalSuccess(res.data));
  } catch (e) {
    console.log("Error changing intervals");
  }
};

const fetchIntervalSuccess = interval => ({
  type: INTERVAL_FETCHED,
  payload: {
    ...interval
  }
});

// -------------- Reducers -----------
const aggregatesReducer = (aggregates = {}, action) => {
  switch (action.type) {
    case AGGREGATES_UPDATED:
      return _.merge({}, aggregates, action.payload);
    default:
      return aggregates;
  }
};

const intervalReducer = (interval = {}, action) => {
  switch (action.type) {
    case INTERVAL_FETCHED:
      return action.payload;
    default:
      return interval;
  }
};

export const reducers = combineReducers({
  aggregates: aggregatesReducer,
  interval: intervalReducer
});
