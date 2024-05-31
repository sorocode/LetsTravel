import { CloseButton } from "@chakra-ui/react";
const SheetHeader = ({ children, onClick, isClose }) => {
  return (
    <div className="flex items-center justify-center h-[52px] border-b-[1px] border-[#e0e0e0] px-[16px] py-0">
      <span className="title font-semibold text-lg">{children}</span>
      <div className="absolute right-6">
        {!isClose && <CloseButton size="sm" onClick={onClick} />}
      </div>
    </div>
  );
};

export default SheetHeader;
