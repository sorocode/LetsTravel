import { useMutation } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { useState } from "react";
import { fetchSpots } from "../util/http";
import { useParams } from "react-router-dom";
import ErrorPage from "../components/UI/Error/ErrorPage";

export const useSearch = ({ items, apiMode, lastTerm, children }) => {
  const params = useParams();
  const [searchTerm, setSearchTerm] = useState("");
  function handleChange(event) {
    if (lastTerm.current) {
      clearTimeout();
    }
    lastTerm.current = setTimeout(() => {
      lastTerm.current = null;
      setSearchTerm(event.target.value);
    }, 1000);
  }
  const searchResults = items.filter((item) =>
    JSON.stringify(item).toLowerCase().includes(searchTerm.toLowerCase())
  );

  const { data, mutate, isPending, isError, error } = useMutation({
    mutationKey: ["spots", { term: searchTerm }],
    mutationFn: () => fetchSpots(searchTerm, params.city),
  });
  function onSubmitHandler(event) {
    event.preventDefault();
    setSearchTerm(event.target.searchTerm.value);
    mutate();
  }
  let content;

  if (!apiMode && searchResults.length >= 1) {
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
    } else {
      content = <p>검색 결과가 없습니다.</p>;
    }
  }
  return {
    data,
    searchTerm,
    setSearchTerm,
    handleChange,
    searchResults,
    mutate,
    content,
    onSubmitHandler,
    isPending,
    isError,
    error,
  };
};
