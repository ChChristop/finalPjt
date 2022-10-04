package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.Constants;
import com.example.demo.service.DishService;
import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;

@RestController
@RequestMapping("/api/dish")
@lombok.extern.slf4j.Slf4j
public class DishController {

	@Autowired
	DishService dishService;

	@Value("${DishDBuploadPath}")
	String dbUploadPath;

	String ip = Constants.IP_PORT;

	@GetMapping("/get")
	public List<Map<String, Object>> get() {

		File fileTest = new File(dbUploadPath);

		if (!fileTest.exists()) {
			fileTest.mkdirs();
		}

		// DB에서 받아온 모양
		List<Map<String, Object>> DBlist = dishService.get();

		// 리턴해줄 List모양
		List<Map<String, Object>> result = new ArrayList<>();

		for (Map<String, Object> resultMap : DBlist) {
			Map<String, Object> map = new HashMap<>();

			map.put("hit", resultMap.get("hit"));
			map.put("writer", resultMap.get("anum"));
			map.put("date", resultMap.get("date"));
			map.put("dish_num", resultMap.get("rcp_seq"));
			map.put("dish_name", resultMap.get("rcp_nm"));
			map.put("cookery", resultMap.get("rcp_way2"));
			map.put("mainIMG", resultMap.get("att_file_no_main"));
			map.put("dish_like", resultMap.get("dish_like"));
			map.put("ate", resultMap.get("ate"));

			String ingSTR = "";

			if (resultMap.get("rcp_parts_dtls") != null) {

				ingSTR = resultMap.get("rcp_parts_dtls").toString();

			} else {

				ingSTR = "재료없음";

			}

			ingSTR = ingSTR.replace("재료", "");
			ingSTR = ingSTR.replaceAll("\n", ", ");

			map.put("ingredient", ingSTR);

			List<String> recipe = new ArrayList<>();

			for (int i = 0; i < 20; i++) {

				String manualId = "manual";
				String manualIdNum = "";

				if (i < 9) {

					manualIdNum = "0" + String.valueOf(i + 1);

				} else {

					manualIdNum = String.valueOf(i + 1);

				}

				manualId += manualIdNum;

				String manualStr = "";

				if (resultMap.get(manualId) != null) {

					manualStr = resultMap.get(manualId).toString();
					manualStr = manualStr.replace("\n", "");

				}

				recipe.add(i, manualStr);
			}

			map.put("recipe", recipe);

			List<String> imgList = new ArrayList<>();

			for (int i = 0; i < 20; i++) {

				String imgId = "manual_img";
				String imgIdNum = "";

				if (i < 9) {

					imgIdNum = "0" + String.valueOf(i + 1);

				} else {

					imgIdNum = String.valueOf(i + 1);

				}

				imgId += imgIdNum;

				String imgStr = "";

				if (resultMap.get(imgId) != null) {

					imgStr = resultMap.get(imgId).toString();
					imgStr = imgStr.replace("\n", "");

				}

				imgList.add(i, imgStr);

			}

			map.put("imgList", imgList);

			result.add(map);
		}

		return result;
	}

	@PostMapping("/comm/add/{mnum}/{RCP_SEQ}")
	public String commAdd(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable String RCP_SEQ) {

		dishComm.setMnum(mnum);
		dishComm.setRCP_SEQ(RCP_SEQ);

		String str = "";
		int i = dishService.commAdd(dishComm);

		if (i > 0) {

			str = Integer.toString(dishComm.getDc_num());

		} else {

			str = "댓글 등록에 실패하였습니다.";
		}

		return str;
	}

	@DeleteMapping("/comm/delete/{mnum}/{dc_num}/{dish_num}")
	public String commDelete(DishComm dishComm, @PathVariable int mnum, @PathVariable int dc_num,
			@PathVariable int dish_num) {

		dishComm.setMnum(mnum);
		dishComm.setDc_num(dc_num);
		dishComm.setRCP_SEQ(Integer.toString(dish_num));

		String str = "";
		int i = dishService.commDelete(dishComm);

		if (i > 0) {
			str = "댓글이 삭제되었습니다.";
		} else {
			str = "댓글 삭제에 실패하였습니다.";
		}

		return str;
	}

	@PutMapping("/comm/edit/{mnum}/{dc_num}")
	public String commEdit(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable int dc_num) {

		dishComm.setMnum(mnum);
		dishComm.setDc_num(dc_num);

		String str = "";
		int i = dishService.commEdit(dishComm);

		if (i > 0) {

			str = "댓글이 수정되었습니다.";

		} else {

			str = "댓글 수정에 실패하였습니다.";

		}

		return str;
	}

	@GetMapping("/search")
	public List<Map<String, Object>> search(@RequestParam String select, @RequestParam String searchI) {

		log.info("[/api/dish/search] [검색어] [{}]", searchI);

		List<Map<String, Object>> dishList = dishService.search(select, searchI);

		return dishList;
	}

	@PostMapping("/add/{anum}")
	public String add(@ModelAttribute DishDB dish, @PathVariable int anum,

			@RequestParam("mainIMG") MultipartFile fileMain, @RequestParam("file01") MultipartFile file01,
			@RequestParam("file02") MultipartFile file02, @RequestParam("file03") MultipartFile file03,
			@RequestParam("file04") MultipartFile file04, @RequestParam("file05") MultipartFile file05,
			@RequestParam("file06") MultipartFile file06) throws Exception {

		File fileTest = new File(dbUploadPath);

		if (!fileTest.exists()) {
			fileTest.mkdirs();
		}

		if (!file01.isEmpty()) {
			String savedName = file01.getOriginalFilename();
			savedName = uploadFile(savedName, file01.getBytes());
			dish.setMANUAL_IMG01(ip + "image/dishDB/" + savedName);
		}

		if (!file02.isEmpty()) {
			String savedName = file02.getOriginalFilename();
			savedName = uploadFile(savedName, file02.getBytes());

			dish.setMANUAL_IMG02(ip + "image/dishDB/" + savedName);

		}

		if (!fileMain.isEmpty()) {
			String savedName = fileMain.getOriginalFilename();
			savedName = uploadFile(savedName, fileMain.getBytes());

			dish.setATT_FILE_NO_MAIN(ip + "image/dishDB/" + savedName);

		}

		if (!file03.isEmpty()) {
			String savedName = file03.getOriginalFilename();
			savedName = uploadFile(savedName, file03.getBytes());

			dish.setMANUAL_IMG03(ip + "image/dishDB/" + savedName);
		}

		if (!file04.isEmpty()) {
			String savedName = file04.getOriginalFilename();
			savedName = uploadFile(savedName, file04.getBytes());

			dish.setMANUAL_IMG04(ip + "image/dishDB/" + savedName);

		}

		if (!file05.isEmpty()) {
			String savedName = file05.getOriginalFilename();
			savedName = uploadFile(savedName, file05.getBytes());

			dish.setMANUAL_IMG05(ip + "image/dishDB/" + savedName);

		}

		if (!file06.isEmpty()) {
			String savedName = file06.getOriginalFilename();
			savedName = uploadFile(savedName, file06.getBytes());

			dish.setMANUAL_IMG06(ip + "image/dishDB/" + savedName);

		}

		int num = dishService.getNum();

		dish.setRCP_SEQ(Integer.toString(num));

		String str = "";

		int i = dishService.add(dish, anum);

		if (i > 0) {

			str = dish.getRCP_SEQ() + "이 등록되었습니다.";

		} else {

			str = "글 등록에 실패하였습니다.";

		}

		return str;

	}

	@PutMapping("/edit/{RCP_SEQ}/{anum}")

	public String edit(@ModelAttribute DishDB dish, @ModelAttribute Dish dish1, @PathVariable int anum,
			@PathVariable int RCP_SEQ, @RequestParam("mainIMG") MultipartFile fileMain,
			@RequestParam("file01") MultipartFile file01, @RequestParam("file02") MultipartFile file02,
			@RequestParam("file03") MultipartFile file03, @RequestParam("file04") MultipartFile file04,
			@RequestParam("file05") MultipartFile file05, @RequestParam("file06") MultipartFile file06)
			throws Exception {

		File fileTest = new File(dbUploadPath);

		if (!fileTest.exists()) {
			fileTest.mkdirs();
		}

		if (!fileMain.isEmpty()) {
			String savedName = fileMain.getOriginalFilename();
			savedName = uploadFile(savedName, fileMain.getBytes());
			dish.setATT_FILE_NO_MAIN(ip + "image/dishDB/" + savedName);

		}

		if (!file01.isEmpty()) {
			String savedName = file01.getOriginalFilename();
			savedName = uploadFile(savedName, file01.getBytes());

			dish.setMANUAL_IMG01(ip + "image/dishDB/" + savedName);

		}

		if (!file02.isEmpty()) {
			String savedName = file02.getOriginalFilename();
			savedName = uploadFile(savedName, file02.getBytes());

			dish.setMANUAL_IMG02(ip + "image/dishDB/" + savedName);

		}

		if (!file03.isEmpty()) {
			String savedName = file03.getOriginalFilename();
			savedName = uploadFile(savedName, file03.getBytes());

			dish.setMANUAL_IMG03(ip + "image/dishDB/" + savedName);

		}

		if (!file04.isEmpty()) {
			String savedName = file04.getOriginalFilename();
			savedName = uploadFile(savedName, file04.getBytes());

			dish.setMANUAL_IMG04(ip + "image/dishDB/" + savedName);

		}

		if (!file05.isEmpty()) {
			String savedName = file05.getOriginalFilename();
			savedName = uploadFile(savedName, file05.getBytes());

			dish.setMANUAL_IMG05(ip + "image/dishDB/" + savedName);

		}

		if (!file06.isEmpty()) {
			String savedName = file06.getOriginalFilename();
			savedName = uploadFile(savedName, file06.getBytes());

			dish.setMANUAL_IMG06(ip + "image/dishDB/" + savedName);

		}

		dish.setRCP_SEQ(Integer.toString(RCP_SEQ));

		String str = "";

		int i = dishService.edit(dish, dish1, RCP_SEQ, anum);

		if (i > 0) {

			str = dish.getRCP_SEQ() + "이 수정되었습니다.";

		} else {

			str = "글 수정에 실패하였습니다.";

		}

		return str;

	}

	@GetMapping("/get/{RCP_SEQ}/{mnum}")
	public Map<String, Object> getOne(@PathVariable int RCP_SEQ, @PathVariable int mnum) {

		Map<String, Object> result = new HashMap<>();

		dishService.upHit(RCP_SEQ);

		int check = dishService.dishLike(mnum, RCP_SEQ);

		String str = "";
		if (check == 1) {

			str = "liked";

		}

		Map<String, Object> resultMap = dishService.getOne(RCP_SEQ);

		Map<String, Object> map = new HashMap<>();

		map.put("liked", str);
		map.put("hit", resultMap.get("hit"));
		map.put("dish_num", resultMap.get("rcp_seq"));
		map.put("dish_name", resultMap.get("rcp_nm"));
		map.put("cookery", resultMap.get("rcp_way2"));
		map.put("mainIMG", resultMap.get("att_file_no_main"));
		map.put("dish_like", resultMap.get("dish_like"));
		map.put("ate", resultMap.get("ate"));
		map.put("writer", resultMap.get("anum"));
		map.put("amdinID", resultMap.get("adminID"));

		if (resultMap.get("editdate") == null) {

			map.put("date", resultMap.get("date"));

		} else {

			map.put("date", resultMap.get("editdate"));

		}

		String ingSTR = "";

		if (resultMap.get("rcp_parts_dtls") != null) {

			ingSTR = resultMap.get("rcp_parts_dtls").toString();

		} else {

			ingSTR = "재료없음";

		}

		ingSTR = ingSTR.replace("재료", "");
		ingSTR = ingSTR.replaceAll("\n", ", ");

		map.put("ingredient", ingSTR);

		List<String> recipe = new ArrayList<>();

		for (int i = 0; i < 20; i++) {

			String manualId = "manual";
			String manualIdNum = "";

			if (i < 9) {

				manualIdNum = "0" + String.valueOf(i + 1);

			} else {

				manualIdNum = String.valueOf(i + 1);

			}

			manualId += manualIdNum;

			String manualStr = "";

			if (resultMap.get(manualId) != null) {

				manualStr = resultMap.get(manualId).toString();
				manualStr = manualStr.replace("\n", "");

			}

			recipe.add(i, manualStr);

		}

		map.put("recipe", recipe);

		List<String> imgList = new ArrayList<>();

		for (int i = 0; i < 20; i++) {

			String imgId = "manual_img";
			String imgIdNum = "";

			if (i < 9) {

				imgIdNum = "0" + String.valueOf(i + 1);

			} else {

				imgIdNum = String.valueOf(i + 1);

			}

			imgId += imgIdNum;

			String imgStr = "";

			if (resultMap.get(imgId) != null) {

				imgStr = resultMap.get(imgId).toString();
				imgStr = imgStr.replace("\n", "");

			}

			imgList.add(i, imgStr);
		}

		map.put("imgList", imgList);

		List<DishComm> commList = dishService.commGet(RCP_SEQ);

		map.put("commList", commList);

		result.put("result", map);

		return result;

	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {

		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		File target = new File(dbUploadPath, savedName);

		FileCopyUtils.copy(fileData, target);

		return savedName;
	}

	@DeleteMapping("/delete/{RCP_SEQ}")
	public String delete(@PathVariable int RCP_SEQ) {

		String str = "";

		int i = dishService.delete(RCP_SEQ);

		if (i > 0) {

			str = "글이 삭제되었습니다.";

		} else {

			str = "글 삭제에 실패하였습니다.";

		}

		return str;
	}

	@GetMapping("/like/{RCP_SEQ}/{mnum}")
	public String goDishLike(@PathVariable int RCP_SEQ, @PathVariable int mnum) {

		String str = "";

		if (dishService.dishLike(mnum, RCP_SEQ) == 0) {

			dishService.goDishLike(RCP_SEQ, mnum);

			str = "좋아요 등록!";

		} else {

			dishService.goDishDislike(RCP_SEQ, mnum);
			str = "좋아요 해제!";

		}

		return str;
	}

}
