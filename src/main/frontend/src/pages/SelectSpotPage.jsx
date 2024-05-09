import { useParams } from "react-router-dom";
import SearchResults from "../components/UI/SearchResults";
import { dummyCities } from "../dummyCities";
import CityItem from "../components/UI/CityItem";

function SelectSpotPage() {
  const params = useParams();
  const selectedCity = dummyCities.find(
    (city) => city.cityName === params.city
  );

  return (
    <div>
      <SearchResults items={selectedCity.spots} searchId="spots">
        {(item) => {
          const isSelected = JSON.stringify(selectedCity).includes(item.id);
          return (
            <CityItem
              key={item.id}
              cityName={item.spotName}
              cityCountry={item.spotName}
              isSelectMode={true}
              // onClick={() => {
              //   isSelected ? handleRemove(item) : handleAdd(item);
              // }}
              isSelected={isSelected}
            />
          );
        }}
      </SearchResults>
    </div>
  );
}

export default SelectSpotPage;
