const NonClickBtn = ({ children, color }) => {
  return (
    <div
      style={{ backgroundColor: color }}
      className="flex justify-center items-center px-2 py-1 rounded-xl"
    >
      {children}
    </div>
  );
};

export default NonClickBtn;
