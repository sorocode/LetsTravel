import DragHandle from "./DragHandle";

const DragHandleEdge = ({ ...props }) => {
  return (
    <div
      {...props}
      className="flex items-end justify-center absolute top-0 left-0 right-0 h-[6px] border-t-2 border-transparent"
    >
      <DragHandle />
    </div>
  );
};

export default DragHandleEdge;
