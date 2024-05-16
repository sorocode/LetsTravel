import { useState, useRef } from "react";
import SearchBar from "./SearchBar";
import { motion } from "framer-motion";
import { useQuery } from "@tanstack/react-query";
import { fetchSpots } from "../../util/http";
import { useParams } from "react-router-dom";
import ErrorPage from "./Error/ErrorPage";
function SearchResults({ apiMode, searchId, items, children }) {
  const lastTerm = useRef();
  const params = useParams();
  const [searchTerm, setSearchTerm] = useState("");
  //FIXME: 새로고침하기 전에 오류 발생 문제 해결
  const searchResults = items.filter((item) =>
    JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase())
  );
  const { data, isPending, isError, error } = useQuery({
    queryKey: ["spots"],
    queryFn: () => fetchSpots(searchTerm, params.city),
    enabled: searchTerm != "",
  });

  function handleChange(event) {
    if (lastTerm.current) {
      clearTimeout();
    }
    lastTerm.current = setTimeout(() => {
      lastTerm.current = null;
      setSearchTerm(event.target.value);
    }, 1000);
  }
  let content;
  if (!apiMode) {
    content = searchResults.map((item, isClicked) => (
      <motion.li layout key={item.id ? item.id : item.citySeq}>
        {children(item, isClicked)}
      </motion.li>
    ));
  } else {
    if (isPending) {
      content = <p>데이터를 가져오는 중입니다...</p>;
    }
    if (isError) {
      content = (
        <ErrorPage
          title="에러발생"
          message={error.info?.message || "여행지를 가져오는 데 실패했습니다."}
        />
      );
    }
    if (data) {
      content = data.places.map((item, isClicked) => (
        <motion.li layout key={item.id ? item.id : item.citySeq}>
          {children(item, isClicked)}
        </motion.li>
      ));
    }
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
        {content}
      </motion.ul>
    </div>
  );
}

export default SearchResults;
