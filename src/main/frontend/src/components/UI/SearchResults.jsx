import { useRef, useState, forwardRef } from "react";
import SearchBar from "./SearchBar";

const SearchResults = forwardRef(function SearchResults(
  { searchId, items, children },
  ref
) {
  const [searchTerm, setSearchTerm] = useState("");
  const searchResults = items.filter((item) =>
    JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase())
  );
  function handleChange(event) {
    if (ref.current) {
      clearTimeout();
    }
    ref.current = setTimeout(() => {
      ref.current = null;
      setSearchTerm(event.target.value);
    }, 1000);
  }
  return (
    <div className="flex flex-col items-center">
      <SearchBar
        searchBarId={searchId}
        placeHolder="어디로 떠나시나요?"
        onChange={handleChange}
        ref={ref}
      />
      <ul className="w-3/4">
        {searchResults.map((item, isClicked) => (
          <li key={item.id}>{children(item, isClicked)}</li>
        ))}
      </ul>
    </div>
  );
});

export default SearchResults;
