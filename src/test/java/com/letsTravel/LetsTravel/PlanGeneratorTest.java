package com.letsTravel.LetsTravel;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.letsTravel.LetsTravel.domain.plan.PlanDetailDTO;
import com.letsTravel.LetsTravel.service.PlanService;

@SpringBootTest
public class PlanGeneratorTest {

	private final PlanService planService;

	@Autowired
	public PlanGeneratorTest(PlanService planService) {
		this.planService = planService;
	}

	@Test
	// 7박 8일, 실제로 내가 간 여행지 샘플플
	public void planGeneratorTestRealTest1() {
		PlanDetailDTO plan = planService
				.getRecommendPlan("JP",
						new ArrayList<String>(Arrays.asList(
								"'ChIJZSX-tMqOGGARFgXf7I1NhcQ'", "'ChIJVze90XnzImARoRp3YqEpbtU'", "'ChIJi3JUZACNGGARXyYn0j7sCkE'", "'ChIJxZW_LuROGGARX3CaDOdyffo'",
								"'ChIJr6UTdvlOGGART30L2LcRlmc'", "'ChIJpQqJoOZOGGAR7FpKUYhZ_gQ'", "'ChIJ65cDn-ZOGGARFgJbe_VZoPo'", "'ChIJSdyjbPxOGGARohA5GYPzeuA'", "'ChIJK9EM68qLGGARacmu4KJj5SA'",
								"'ChIJhxxszamMGGARcuAXpFunolU'", "'ChIJSXJEFqmMGGAR8ctb-YsQ_xM'", "'ChIJscDhJ4SLGGARbx0GlzPi9ng'", "'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
								"'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJf2129yiMGGAR9I6Yoe-1uHY'", "'ChIJh7eDrwCPGGARCs9fpCkKS2U'", "'ChIJo8VKlCCMGGARarjlIsaLY1g'", "'ChIJMwpiebSMGGARPr_454zHvDQ'",
								"'ChIJFZmQijCNGGARTGdM5ATq-aw'", "'ChIJ7RsWzB6MGGARi3xBv7bkl_M'", "'ChIJZzSYoB2MGGARfBcicn7sx28'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJtQ46c0RPGGARoL-K9wNTcZ0'",
								"'ChIJIRZ85hZPGGARffzavFo4QRk'", "'ChIJbWVFlTCKGGARr5l6ox-7_CA'", "'ChIJp_dRJOOLGGARo5GGDjBnfrw'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'",
								"'ChIJPYwFnNGNGGARYEeXwiYQPe8'", "'ChIJrw6t4tONGGARm4SASOVzzLc'", "'ChIJ-X4hzjKMGGARDSsGeHOACgw'", "'ChIJ8VLVBuTsGGARfIDGqQDmC0Q'", "'ChIJ18IK6x2KGGARKTc2yLS-030'",
								"'ChIJscIAGGrzGGAROoW9kySUQYQ'", "'ChIJwQY4mB2MGGARgn-IBKS98Q8'", "'ChIJAylxefmLGGARmouNFJGoD3E'", "'ChIJU9ZPE2-NGGARwiJyx0Id61E'")),
						"ChIJZSX-tMqOGGARFgXf7I1NhcQ", "ChIJVze90XnzImARoRp3YqEpbtU", 7)
				.getPlan();
	}

	@Test
	// 2박 3일, 도시 5개 이상
	public void planGeneratorTest1() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'",
						"'ChIJVze90XnzImARoRp3YqEpbtU'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", "ChIJVze90XnzImARoRp3YqEpbtU", 3).getPlan();
	}

	@Test
	// 2박 3일, 도시 3~4개
	public void planGeneratorTest2() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJoTcat9SMGGAR6GGG8zdcZvE'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'", "'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'",
						"'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'", "'ChIJVze90XnzImARoRp3YqEpbtU'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", "ChIJVze90XnzImARoRp3YqEpbtU", 3).getPlan();
	}

	@Test
	// 3박 4일, 도시 많이
	public void planGeneratorTest3() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'",
						"'ChIJVze90XnzImARoRp3YqEpbtU'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", "ChIJVze90XnzImARoRp3YqEpbtU", 4).getPlan();
	}

	@Test
	// 2박 3일, 도시 5개 이상, 숙소 없음
	public void planGeneratorTest4() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJVze90XnzImARoRp3YqEpbtU'")),
				null, "ChIJVze90XnzImARoRp3YqEpbtU", 3).getPlan();
	}

	@Test
	// 2박 3일, 도시 5개 이상, 공항 없음
	public void planGeneratorTest5() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", null, 3).getPlan();
	}

	@Test
	// 2박 3일, 도시 5개 이상, 공항・숙소 없음
	public void planGeneratorTest6() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'")),
				null, null, 3).getPlan();
	}

	@Test
	// 4박 5일, 도시 많이
	public void planGeneratorTest7() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'",
						"'ChIJVze90XnzImARoRp3YqEpbtU'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", "ChIJVze90XnzImARoRp3YqEpbtU", 5).getPlan();
	}

	@Test
	// 5박 6일, 10도시
	public void planGeneratorTest8() {
		PlanDetailDTO plan = planService.getRecommendPlan("JP",
				new ArrayList<String>(Arrays.asList("'ChIJCewJkL2LGGAR3Qmk0vCTGkg'", "'ChIJ35ov0dCOGGARKvdDH7NPHX0'", "'ChIJ8T1GpMGOGGARDYGSgpooDWw'", "'ChIJoTcat9SMGGAR6GGG8zdcZvE'",
						"'ChIJPfFaQhOMGGAR-QPbNQoAG6M'", "'ChIJszdHEQN9GGARy9MJ1TY22eQ'", "'ChIJPyOTG8KMGGARh_IXobWxHmo'", "'ChIJw2qQRZuOGGARWmROEiM2y7E'", "'ChIJx1e971eLGGARum8w_tnpFwI'",
						"'ChIJ4Rr2JWiLGGARcyRSHuZ-9G8'", "'ChIJ0YwG28aOGGARvRKAXIBWqNk'", "'ChIJPd37MMGOGGARvJ2hfxoiNVE'", "'ChIJ5SZMmreMGGARcz8QSTiJyo8'", "'ChIJx0V7jrqLGGAR_VT5Fj3yNZ8'",
						"'ChIJC3Cf2PuLGGAROO00ukl8JwA'", "'ChIJszdHEQN9GGARJS23SnAdR0E'", "'ChIJSeco5wiJGGARItbTS8lQ5G0'", "'ChIJIbdI7PaJGGARYrhCahk1Ky8'", "'ChIJM66ftJuOGGARVLLDR_ybEKE'",
						"'ChIJVze90XnzImARoRp3YqEpbtU'")),
				"ChIJM66ftJuOGGARVLLDR_ybEKE", "ChIJVze90XnzImARoRp3YqEpbtU", 7).getPlan();
	}
}
