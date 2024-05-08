import SelectDateArea from "../components/UI/SelectDateArea";
import SelectedCityList from "../components/UI/SelectedCityList";
function SelectTermPage() {
  return (
    <section className="flex flex-col w-3/4 mt-10 mx-auto">
      <SelectedCityList />
      <hr className="mt-5 mb-10" />

      <article className="mx-auto">
        <SelectDateArea />
      </article>
    </section>
  );
}

export default SelectTermPage;
