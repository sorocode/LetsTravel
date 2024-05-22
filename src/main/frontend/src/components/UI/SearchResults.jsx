import { useRef } from "react";
import SearchBar from "./SearchBar";
import { motion } from "framer-motion";
import ButtonSmall from "./Buttons/ButtonSmall";
import { useSearch } from "../../hooks/useSearch";
import QuickSearchButton from "./Buttons/QuickSearchButton";
function SearchResults({
  spotMode,
  apiMode,
  searchId,
  items,
  changeMode,
  cityName,
  children,
}) {
  const lastTerm = useRef();

  const { content, onSubmitHandler, handleChange, onQuickSearchHandler } =
    useSearch({
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
        onChangeMode={changeMode}
        onSubmit={onSubmitHandler}
        onChange={handleChange}
        ref={lastTerm}
      />
      {(apiMode || spotMode) && (
        <div className="flex justify-center items-center mb-4 gap-4">
          <QuickSearchButton
            emoji="🏨"
            title="호텔"
            cityName={cityName}
            onQuickSearchHandler={onQuickSearchHandler}
          />
          <QuickSearchButton
            emoji="🍽️"
            title="맛집"
            cityName={cityName}
            onQuickSearchHandler={onQuickSearchHandler}
          />
          <QuickSearchButton
            emoji="☕️"
            title="카페"
            cityName={cityName}
            onQuickSearchHandler={onQuickSearchHandler}
          />
          <QuickSearchButton
            emoji="🔥"
            title="핫플"
            cityName={cityName}
            onQuickSearchHandler={onQuickSearchHandler}
          />
        </div>
      )}
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
