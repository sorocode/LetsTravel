export const getDaysDifference = (startDate, endDate) => {
  const oneDay = 24 * 60 * 60 * 1000; // 하루의 밀리초
  const start = new Date(startDate);
  const end = new Date(endDate);
  const diffInMilliseconds = end - start;
  return Math.round(diffInMilliseconds / oneDay);
};
