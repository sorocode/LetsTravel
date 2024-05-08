import { motion } from "framer-motion";
import { useBottomSheet } from "../../../hooks/useBottomSheet";
import { createPortal } from "react-dom";
import DragHandleEdge from "./DragHandleEdge";
import SheetHeader from "./SheetHeader";

function BottomSheet({ title, children }) {
  const { onDragEnd, controls, onToggle, handleDoubleClick } = useBottomSheet();

  return createPortal(
    <motion.div
      drag="y"
      onDragEnd={onDragEnd}
      initial="hidden"
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
        closed: { y: "0%" },
      }}
      dragConstraints={{ top: "-60%" }}
      dragElastic={0.2}
      className="inline-block bg-white w-full mx-1 h-[700px] drop-shadow-2xl border-t-[0.8px] border-[#E0E0E0] rounded-t-3xl overflow-hidden z-[1000] text-center fixed"
      onClick={onToggle}
    >
      <DragHandleEdge onClick={handleDoubleClick} />
      <SheetHeader title={title} />
      {children}
    </motion.div>,
    document.getElementById("bottom-sheet")
  );
}

export default BottomSheet;
