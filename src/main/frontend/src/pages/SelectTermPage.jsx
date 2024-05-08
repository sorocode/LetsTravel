import Button from "../components/UI/Buttons/Button";
import SelectDateArea from "../components/UI/SelectDateArea";
import SelectedCityList from "../components/UI/SelectedCityList";

function SelectTermPage() {
  return (
    <section className="flex flex-col w-3/4 mt-20 mx-auto">
      <article>
        <SelectedCityList />
      </article>
      <hr className="mt-5 mb-20" />
      <article className="mx-auto">
        <SelectDateArea />
      </article>
    </section>
  );
}

export default SelectTermPage;
