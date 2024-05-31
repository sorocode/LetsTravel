export function transformPlaceData(placeData) {
  // Extract place detail
  const placeDetail = {
    id: placeData.id,
    displayName: placeData.displayName.text,
    languageCode: placeData.displayName.languageCode,
    formattedAddress: placeData.formattedAddress || "",
    location: {
      latitude: placeData.location.latitude,
      longitude: placeData.location.longitude,
    },
    googleMapsUri: placeData.googleMapsUri,
  };

  // Extract cities
  const cities = [];
  //FIXME: 일단 JP로 설정했지만 이후 동적으로 변경할 것
  let countryCode = "JP";
  placeData.addressComponents.forEach((component) => {
    if (component.types.includes("country")) {
      countryCode = component.shortText;
    }
    if (
      component.types.includes("administrative_area_level_1") ||
      component.types.includes("administrative_area_level_2")
    ) {
      cities.push({
        countryCode: countryCode,
        cityName: component.longText,
      });
    }
  });

  // Extract types
  let types = placeData.types.filter(
    (type) =>
      ![
        "country",
        "administrative_area_level_1",
        "administrative_area_level_2",
      ].includes(type)
  );
  if (types.length === 0) {
    // If all types are filtered out, do not proceed
    return null;
  }
  if (
    types.includes("establishment") &&
    types.includes("point_of_interest") &&
    types.length === 2
  ) {
    types = ["etc"];
  } else {
    types = types.filter(
      (type) => !["establishment", "point_of_interest"].includes(type)
    );
  }

  // Determine primaryTypeDetail
  let primaryTypeDetail = {
    primaryTypeDisplayName: "기타",
    primaryType: "etc",
  };
  if (placeData.primaryType && placeData.primaryTypeDisplayName) {
    primaryTypeDetail = {
      primaryTypeDisplayName:
        typeof placeData.primaryTypeDisplayName === "object"
          ? placeData.primaryTypeDisplayName.text
          : placeData.primaryTypeDisplayName,
      primaryType: placeData.primaryType,
    };
  } else {
    primaryTypeDetail = {
      primaryTypeDisplayName: "기타",
      primaryType: "etc",
    };
  }

  // Construct the final result
  const result = {
    placeDetail: placeDetail,
    cities: cities,
    types: types,
    primaryTypeDetail: primaryTypeDetail,
  };

  return result;
}
