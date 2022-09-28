import { useState } from "react";



export default function Date() {
  const [dateValue, setDateValue] = useState(null);
  const [dateValueInEpoch, setDateValueInEpoch] = useState(null);
  function handleDateUpdate(e) {
    const dateValue = e.target.value;
    console.log("dateValue", dateValue);
    setDateValue(dateValue);
    const dateValueInEpoch = new Date(dateValue).getTime();
    console.log("dateValueInEpoch", dateValueInEpoch);
    setDateValueInEpoch(dateValueInEpoch);
  }
  return (
    <div className="date">
      <span> </span>
      <input type="date" onChange={(e) => handleDateUpdate(e)} />
      {dateValue ? (
        <div className="dateformats">
          <div>{dateValue}</div>
          {/* <div>Date value in epoch format: {dateValueInEpoch}</div> */}
        </div>
      ) : null}
    </div>
  );
}
