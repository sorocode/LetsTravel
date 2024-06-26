import Timeline from "@mui/lab/Timeline";
import TimelineItem from "@mui/lab/TimelineItem";
import TimelineSeparator from "@mui/lab/TimelineSeparator";
import TimelineConnector from "@mui/lab/TimelineConnector";
import TimelineContent from "@mui/lab/TimelineContent";
import TimelineOppositeContent from "@mui/lab/TimelineOppositeContent";
import TimelineDot from "@mui/lab/TimelineDot";
import FastfoodIcon from "@mui/icons-material/Fastfood";
import LaptopMacIcon from "@mui/icons-material/LaptopMac";
import HotelIcon from "@mui/icons-material/Hotel";
import RepeatIcon from "@mui/icons-material/Repeat";
import Typography from "@mui/material/Typography";
import CustomMarker from "./CustomMarker";

export default function Itinery({ days, scheduleData }) {
  return (
    <>
      <Timeline position="alternate">
        {days.map((day) => {
          let bgColor;
          console.log("day", day);
          switch (parseInt(day)) {
            case 1:
              bgColor = "#F3B385";
              break;
            case 2:
              bgColor = "#c985f3";
              break;
            case 3:
              bgColor = "#85F389";
              break;
            default:
              bgColor = "#7BC9FF";
              break;
          }
          return (
            <>
              <span className="font-semibold">Day{day}</span>
              {scheduleData[parseInt(day)].map((spot) => {
                const spotId = spot.spotId || spot.placeName;
                const spotName = spot.spotName || spot.placeName;
                let placeType;
                if (
                  spotName.toLowerCase().includes("hotel") ||
                  spotName.includes("호텔")
                ) {
                  placeType = "호텔";
                } else if (
                  spotName.toLowerCase().includes("airport") ||
                  spotName.toLowerCase().includes("air port") ||
                  spotName.includes("공항")
                ) {
                  placeType = "공항";
                } else {
                  placeType = "관광명소";
                }
                console.log(spotName);
                return (
                  <>
                    <TimelineItem key={spotId}>
                      <TimelineOppositeContent
                        sx={{ m: "auto 0" }}
                        align="right"
                        variant="body2"
                        color="text.secondary"
                      ></TimelineOppositeContent>
                      <TimelineSeparator>
                        <TimelineConnector />
                        {/* <TimelineDot> */}
                        <CustomMarker color={bgColor}>
                          {scheduleData[day].indexOf(spot) + 1}
                        </CustomMarker>
                        {/* </TimelineDot> */}
                        <TimelineConnector />
                      </TimelineSeparator>
                      <TimelineContent sx={{ py: "12px", px: 2 }}>
                        <Typography component="p">
                          <span className="font-semibold">{spotName}</span>
                        </Typography>
                        <Typography component="p">
                          <span className="text-xs">{placeType}</span>
                        </Typography>
                      </TimelineContent>
                    </TimelineItem>
                  </>
                );
              })}
            </>
          );
        })}
      </Timeline>
    </>
  );
}
