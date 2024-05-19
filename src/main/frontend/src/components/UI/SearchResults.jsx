import { useRef } from "react";
import SearchBar from "./SearchBar";
import { motion } from "framer-motion";

import { useSearch } from "../../hooks/useSearch";
function SearchResults({ apiMode, searchId, items, children }) {
  const lastTerm = useRef();

  const { content, onSubmitHandler, handleChange } = useSearch({
    items,
    apiMode,
    lastTerm,
    children,
  });

  return (
    <div className="flex flex-col items-center">
      <SearchBar
        searchBarId={searchId}
        placeHolder="어디로 떠나시나요?"
        onSubmit={onSubmitHandler}
        onChange={handleChange}
        ref={lastTerm}
      />
      <motion.ul
        initial={{ opacity: 0.5, y: 100 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8 }}
        className="w-3/4"
      >
        {content}
      </motion.ul>
    </div>
  );
}

export default SearchResults;
