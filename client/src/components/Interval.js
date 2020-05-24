import { connect } from "react-redux";
import { changeInterval, getInterval } from "../actions/aggregates";
import React, { useEffect, useState } from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import { Card, CardContent } from "@material-ui/core";

const Interval = props => {
  useEffect(() => {
    // code to run on component mount
    props.getInterval();
  }, []);

  const { intervalInSeconds } = props;
  const [interval, setInterval] = useState(0);
  if (intervalInSeconds && !interval) {
    setInterval(intervalInSeconds);
  }
  function handleSubmit(event) {
    event.preventDefault();
    props.changeInterval(interval);
  }

  return (
      <Card spacing={5}>
        <CardContent>
          <form onSubmit={handleSubmit}>
            <TextField
              id="standard-number"
              label="Interval in seconds"
              type="number"
              InputLabelProps={{
                shrink: true,
              }}
              value={interval}
              onInput={ e => setInterval(e.target.value)}
            />
            <Button type={"submit"} >Submit</Button>
          </form>
        </CardContent>

      </Card>
  )
}


const mapStateToProps = state => {
  return {
    intervalInSeconds: state.interval.intervalInSeconds
  };
};

export default connect(mapStateToProps, { getInterval, changeInterval })(Interval);
