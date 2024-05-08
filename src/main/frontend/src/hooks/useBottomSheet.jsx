import { useAnimation } from "framer-motion";
import { useState, useEffect } from "react";
import { usePrevious } from "./usePrevious";

export const useBottomSheet = () => {
  const [isOpen, setIsOpen] = useState(false);
  function onClose() {
    setIsOpen(false);
  }

  function onOpen() {
    setIsOpen(true);
  }

  function onToggle() {
    setIsOpen(!isOpen);
  }
  const controls = useAnimation();
  const prevIsOpen = usePrevious(isOpen);

  function onDragEnd(event, info) {
    const shouldClose =
      info.velocity.y > 20 || (info.velocity.y >= 0 && info.point.y > 45);
    if (shouldClose) {
      controls.start("hidden");
      onClose();
    } else {
      controls.start("visible");

      onOpen();
    }
  }

  useEffect(() => {
    if (prevIsOpen && !isOpen) {
      controls.start("hidden");
    } else if (!prevIsOpen && isOpen) {
      controls.start("visible");
    }
  }, [controls, isOpen, prevIsOpen]);

  const handleDoubleClick = (e) => {
    switch (e.detail) {
      case 1:
        // console.log("click");
        break;
      case 2:
        if (!prevIsOpen && isOpen) {
          controls.start("hidden");
          setIsOpen(false);
          // console.log("double click visible");
        } else if (prevIsOpen && !isOpen) {
          controls.start("visible");
          setIsOpen(true);

          // console.log("double click hidden");
        }

        break;

      default:
        return;
    }
  };
  return {
    onDragEnd,
    controls,
    setIsOpen,
    isOpen,
    handleDoubleClick,
    onToggle,
  };
};
