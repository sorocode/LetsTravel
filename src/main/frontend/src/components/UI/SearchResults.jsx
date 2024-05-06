import { useState, forwardRef, useRef } from "react";
import SearchBar from "./SearchBar";

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
      <ul className="w-3/4">
        {searchResults.map((item, isClicked) => (
          <li key={item.id}>{children(item, isClicked)}</li>
        ))}
      </ul>
    </div>
  );
}

export default SearchResults;
