export const getEndDate = (planStart, planNDays) => {
  // 시작 날짜를 Date 객체로 변환
  const startDate = new Date(planStart);

  // 기간만큼 더하기 (날짜 계산)
  const planEndDate = new Date(startDate);
  planEndDate.setDate(startDate.getDate() + planNDays);

  // 종료 날짜를 "YYYY-MM-DD" 형식으로 변환
  const year = planEndDate.getFullYear();
  const month = String(planEndDate.getMonth() + 1).padStart(2, "0");
  const day = String(planEndDate.getDate()).padStart(2, "0");
  const planEnd = `${year}-${month}-${day}`;

  return planEnd;
};
