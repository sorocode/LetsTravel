import searchIcon from "../../assets/icons/search_icon.svg";

const SearchBar = ({ searchBarId, placeHolder }) => {
  return (
    <div className="container flex border-gray-600 border-[0.5px] w-4/5 rounded-xl justify-between px-6 py-2 mt-20 mb-5">
      <input
        type="text"
        name={searchBarId}
        id={searchBarId}
        placeholder={placeHolder}
        className="w-full"
      />
      <button>
        <img src={searchIcon} alt="searchIcon" />
      </button>
    </div>
  );
};

export default SearchBar;
