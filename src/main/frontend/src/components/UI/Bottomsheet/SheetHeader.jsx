const SheetHeader = ({ children, onClick }) => {
  return (
    <div className="flex items-center justify-between h-[52px] border-b-[1px] border-[#e0e0e0] px-[16px] py-0">
      <span className="title">{children}</span>
      <button onClick={onClick}>닫기</button>
    </div>
  );
};

export default SheetHeader;
