import { motion } from "framer-motion";
import { useBottomSheet } from "../../hooks/useBottomSheet";
import { createPortal } from "react-dom";

function BottomSheet({ children }) {
  const { onDragEnd, controls, onToggle } = useBottomSheet();

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
        visible: { y: "-60%" },
        hidden: { y: "-15%" },
        closed: { y: "0%" },
      }}
      dragConstraints={{ top: "-60%" }}
      dragElastic={0.2}
      className="inline-block bg-white w-full mx-1 h-[300px] drop-shadow-2xl border-t-[0.8px] border-[#E0E0E0] rounded-xl overflow-hidden z-[1000] text-center fixed"
      //   style={{
      //     display: "inline-block",
      //     backgroundColor: "white",
      //     width: 320,
      //     height: 768,
      //     border: "1px solid #E0E0E0",
      //     boxShadow:
      //       "0px 2px 5px rgba(0, 0, 0, 0.06), 0px 2px 13px rgba(0, 0, 0, 0.12)",
      //     borderRadius: "13px 13px 0px 0px",
      //     overflow: "hidden",
      //     zIndex: 1000,
      //   }}
      onClick={onToggle}
    >
      {children}
    </motion.div>,
    document.getElementById("bottom-sheet")
  );
}

export default BottomSheet;
