import { CloseButton } from "@chakra-ui/react";
const SheetHeader = ({ children, onClick, isClose }) => {
  return (
    <div className="flex items-center justify-between h-[52px] border-b-[1px] border-[#e0e0e0] px-[16px] py-0">
      <span className="title">{children}</span>
      {!isClose && (
        <button onClick={onClick}>
          <CloseButton size="sm" />
        </button>
      )}
    </div>
  );
};

export default SheetHeader;
