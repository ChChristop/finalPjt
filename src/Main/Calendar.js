import React from "react";
import "./Calendar.css";

class Calendar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      currentMonth: new Date()
    };
  }
  _getWeekdays() { //Day
    return ["일", "월", "화", "수", "목", "금", "토"];
  }
  _getMonths() { //month
    return [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    ];
  }
  _nextMonth = () => {
    const currentDate = 1;
    let currentMonth = this.state.currentMonth.getMonth();
    let currentYear = this.state.currentMonth.getFullYear();
    currentMonth += 1;
    if (currentMonth > 11) {
      currentYear++;
      currentMonth = 0;
    }
    let d = new Date(currentMonth + 1 + "/" + currentDate + "/" + currentYear);
    this.setState({
      currentMonth: d
    });
  };
  _previousMonth = () => {
    const currentDate = 1;
    let currentMonth = this.state.currentMonth.getMonth();
    let currentYear = this.state.currentMonth.getFullYear();
    currentMonth -= 1;
    if (currentMonth < 0) {
      currentYear--;
      currentMonth = 11;
    }
    let d = new Date(currentMonth + 1 + "/" + currentDate + "/" + currentYear);
    this.setState({
      currentMonth: d
    });
  };
  _getFirstDayOfCurrentMonth(date) {
    const firstDate = new Date(
      date.getMonth() + 1 + "/1/" + date.getFullYear()
    );
    return firstDate.getDay();
  }
  _getTotalDaysInCurrentMonth(date) {
    const totalDays = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    return totalDays.getDate();
  }
  render() {
    const today = new Date();
    let weekdaysName = this._getWeekdays().map(day => {
      return <th className="week-day">{day}</th>;
    });
    let blanks = [];
    for (
      let i = 0;
      i < this._getFirstDayOfCurrentMonth(this.state.currentMonth);
      i++
    ) {
      blanks.push(<td>{""}</td>);
    }
    let daysInMonth = [];
    for (
      let d = 1;
      d <= this._getTotalDaysInCurrentMonth(this.state.currentMonth);
      d++
    ) {
      let currentDayClass =
        d === today.getDate() &&
        today.getMonth() === this.state.currentMonth.getMonth() &&
        today.getFullYear() === this.state.currentMonth.getFullYear()
          ? "today"
          : "";
      daysInMonth.push(<td className={`${currentDayClass}`}>{d}</td>);
    }
    var totalSlots = [...blanks, ...daysInMonth];
    let rows = []; // month data
    let cells = []; // week data

    console.log(totalSlots);
    totalSlots.forEach((row, i) => {
      if (i % 7 !== 0) {
        cells.push(row); // if index not equal 7 that means not go to next week
      } else {
        rows.push(cells); // when reach next week we contain all td in last week to rows
        cells = []; // empty container
        cells.push(row); // in current loop we still push current row to new container
      }
      if (i === totalSlots.length - 1) {
        // when end loop we add remain date
        rows.push(cells);
      }

      // console.log(row);
    });
    // console.log(cells);
    let daysinmonth = rows.map((d, i) => {
      return <tr>{d}</tr>;
    });
    return (
      <div>
        <h2>
          {this._getMonths()[this.state.currentMonth.getMonth()]}{" "}
          {this.state.currentMonth.getFullYear()}
        </h2>
        <div className="flex-area">
          <div className="button-area">
            <button className="light1"  variant="light" onClick={this._previousMonth}>PREV</button>
            <button className="light1" onClick={this._nextMonth}>NEXT</button>
          </div>
        </div>
        <table className="calendar-day">
          <thead>
            <tr>{weekdaysName}</tr>
          </thead>
          <tbody>{daysinmonth}</tbody>
        </table>
      </div>
    );
  }
}

export default Calendar;