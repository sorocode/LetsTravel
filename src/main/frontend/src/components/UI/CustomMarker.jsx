const CustomMarker = ({ color, children }) => {
  return (
    <div
      style={{ backgroundColor: color }}
      className="rounded-[86px] border-solid border-[#000000] border p-2.5 flex flex-col gap-2.5 items-center justify-center shrink-0 w-[30px] h-[30px] relative "
    >
      <span>{children}</span>
    </div>
  );
};

export default CustomMarker;
