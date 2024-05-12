import { useState, useRef } from "react";
import SearchBar from "./SearchBar";
import { motion } from "framer-motion";

function SearchResults({ searchId, items, children }) {
  const lastTerm = useRef();
  const [searchTerm, setSearchTerm] = useState("");
  const searchResults = items.filter((item) =>
    JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase())
  );

  function handleChange(event) {
    if (lastTerm.current) {
      clearTimeout();
    }
    lastTerm.current = setTimeout(() => {
      lastTerm.current = null;
      setSearchTerm(event.target.value);
    }, 1000);
  }
  return (
    <div className="flex flex-col items-center">
      <SearchBar
        searchBarId={searchId}
        placeHolder="어디로 떠나시나요?"
        onChange={handleChange}
        ref={lastTerm}
      />
      <motion.ul
        initial={{ opacity: 0.5, y: 100 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8 }}
        className="w-3/4"
      >
        {searchResults.map((item, isClicked) => (
          <motion.li layout key={item.id}>
            {children(item, isClicked)}
          </motion.li>
        ))}
      </motion.ul>
    </div>
  );
}

export default SearchResults;
