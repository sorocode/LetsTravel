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

export default function TimeLine({ days, scheduleData }) {
  console.log("days", days);
  return (
    <>
      <Timeline position="alternate">
        {days.map((day) => {
          const daySchedule = scheduleData.find(
            (schedule) => schedule.dateSeq === day
          );

          let bgColor;
          switch (daySchedule.dateSeq) {
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
              <span className="font-semibold">Day{daySchedule.dateSeq}</span>;
              {daySchedule.scheduleDetail.map((spot, index) => {
                return (
                  <>
                    <TimelineItem key={index}>
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
                          {spot.visitSeq}
                        </CustomMarker>
                        {/* </TimelineDot> */}
                        <TimelineConnector />
                      </TimelineSeparator>
                      <TimelineContent sx={{ py: "12px", px: 2 }}>
                        <Typography component="p">
                          <span className="font-semibold">
                            {spot.placeName}
                          </span>
                        </Typography>
                        <Typography component="p">
                          <span className="text-xs">{spot.primaryType}</span>
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
