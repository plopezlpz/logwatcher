import React, { Component } from "react";
import Container from "@material-ui/core/Container";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import { ThemeProvider } from "@material-ui/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import TickerTable from "./AggregateTable";
import Interval from "./Interval";

const theme = createMuiTheme({
  palette: {
    type: "dark"
  }
});

class App extends Component {
  render() {
    return (
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Container maxWidth="md">
          <h1>Log Severity Count</h1>
          <Interval />
          <TickerTable />

        </Container>
      </ThemeProvider>
    );
  }
}

export default App;
