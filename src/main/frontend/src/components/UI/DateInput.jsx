const DateInput = ({ isStart }) => {
  return (
    <input
      className="w-64 h-11 border-gray-800 border rounded-2xl px-4"
      type="date"
      name={isStart ? "startDate" : "endDate"}
      id={isStart ? "startDate" : "endDate"}
    />
  );
};

export default DateInput;
