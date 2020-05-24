import React, { useEffect, useRef } from "react";
import { connect } from "react-redux";
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@material-ui/core";
import { updateAggregates } from "../actions/aggregates";
import { onAggregatesReceived } from "../aggregatesWS";
import { SEVERITY_TYPES } from "../variables";
import "./styles.css";

const AggregateTable = props => {

  const tableRef = useRef();

  useEffect(() => {
    // code to run on component mount
    onAggregatesReceived(ticket => {
      props.updateAggregates(ticket);
      tableRef.current.classList.add("highlight");
      setTimeout(() => {
        tableRef.current.classList.remove("highlight")
      }, 300);
    });
  }, []);

  const { severityCounts } = props;
  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table" ref={tableRef} className={"aggtable"}>
        <TableHead style={{ backgroundColor: "#333e63" }}>
          <TableRow>
            <TableCell>Severity</TableCell>
            <TableCell>Count</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {SEVERITY_TYPES.map(type => (
            <TableRow hover role="checkbox" key={type}>
              <TableCell component="th" scope="row">
                {type.toUpperCase()}
              </TableCell>
              <TableCell align="left">{severityCounts[type]}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

const mapStateToProps = state => {
  return {
    severityCounts: state.aggregates,
  };
};

export default connect(mapStateToProps, { updateAggregates })(AggregateTable);
