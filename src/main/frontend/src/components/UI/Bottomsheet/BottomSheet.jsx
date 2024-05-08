import { motion } from "framer-motion";
import { useBottomSheet } from "../../../hooks/useBottomSheet";
import { createPortal } from "react-dom";
import DragHandleEdge from "./DragHandleEdge";
import SheetHeader from "./SheetHeader";
import NonClickBtn from "../Buttons/NonClickBtn";
import up_icon from "../../../assets/icons/up_rounded.svg";
function BottomSheet({ title, children }) {
  const {
    isClose,
    setIsOpen,
    setIsClose,
    onDragEnd,
    controls,
    onToggle,
    handleDoubleClick,
  } = useBottomSheet();

  return createPortal(
    <div>
      {isClose && (
        <div className="fixed bottom-16 right-2">
          <button
            className="px-2 py-1 border-[1px] border-black rounded-xl"
            onClick={() => {
              setIsClose(false);
              setIsOpen(true);
            }}
          >
            <img src={up_icon} alt="up_icon" />
          </button>
        </div>
      )}
      <motion.div
        drag="y"
        onDragEnd={onDragEnd}
        initial="closed"
        animate={controls}
        transition={{
          type: "spring",
          damping: 40,
          stiffness: 400,
        }}
        variants={{
          // hidden: { y: "calc(100% - 256px)" },
          // visible: { y: "10%" },
          visible: { y: "-40%" },
          hidden: { y: "0%" },
          closed: { y: "100%" },
        }}
        dragConstraints={{ top: "-60%" }}
        dragElastic={0.2}
        className="inline-block bg-white w-full mx-1 h-[700px] drop-shadow-2xl border-t-[0.8px] border-[#E0E0E0] rounded-t-3xl overflow-hidden text-center fixed"
        onDoubleClick={onToggle}
      >
        <DragHandleEdge onClick={handleDoubleClick} />
        <SheetHeader
          onClick={() => {
            setIsClose(true);
            setIsOpen(false);
          }}
        >
          {title}
        </SheetHeader>
        {children}
      </motion.div>
    </div>,
    document.getElementById("bottom-sheet")
  );
}

export default BottomSheet;
