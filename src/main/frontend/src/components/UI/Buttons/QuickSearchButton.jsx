import ButtonSmall from "./ButtonSmall";

const QuickSearchButton = ({
  cityName,
  emoji,
  title,
  onQuickSearchHandler,
}) => {
  return (
    <ButtonSmall onClick={() => onQuickSearchHandler(title)}>
      <div className="flex flex-col justify-center items-center">
        <div>{emoji}</div>
        <div>{title}</div>
      </div>
    </ButtonSmall>
  );
};
export default QuickSearchButton;
