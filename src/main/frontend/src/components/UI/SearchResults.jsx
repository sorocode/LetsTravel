import { useRef, useState } from "react";
import SearchBar from "./SearchBar";

const SearchResults = ({ searchId, items, children }) => {
  const lastChange = useRef();
  const [searchTerm, setSearchTerm] = useState("");
  const searchResults = items.filter((item) =>
    JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase())
  );
  function handleChange(event) {
    if (lastChange.current) {
      clearTimeout();
    }
    lastChange.current = setTimeout(() => {
      lastChange.current = null;
      setSearchTerm(event.target.value);
    }, 1000);
  }
  return (
    <div className="flex flex-col items-center">
      <SearchBar
        searchBarId={searchId}
        placeHolder="어디로 떠나시나요?"
        onChange={handleChange}
        ref={lastChange}
      />
      <ul className="w-3/4">
        {searchResults.map((item) => (
          <li key={item.id}>{children(item)}</li>
        ))}
      </ul>
    </div>
  );
};

export default SearchResults;
