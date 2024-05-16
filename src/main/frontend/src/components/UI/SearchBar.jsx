import { forwardRef } from "react";
import searchIcon from "../../assets/icons/search_icon.svg";

const SearchBar = forwardRef(function SearchBar(
  { onSubmit, searchBarId, placeHolder, ...props },
  ref
) {
  return (
    <form
      onSubmit={onSubmit}
      className="container flex border-gray-600 border-[0.9px] w-4/5 rounded-xl justify-between px-4 py-2 mt-20 mb-5"
    >
      <input
        type="text"
        name="searchTerm"
        id={searchBarId}
        placeholder={placeHolder}
        className="w-full"
        ref={ref}
        {...props}
      />
      <button>
        <img src={searchIcon} alt="searchIcon" />
      </button>
    </form>
  );
});

export default SearchBar;
