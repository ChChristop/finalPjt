package com.example.demo.controller;

import java.io.File;
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
import com.example.demo.service.AteService;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ate")
@Slf4j
public class AteController {

	@Autowired
	AteService ateService;

	@Value("${AteuploadPath}")
	String uploadPath;

	String ip = Constants.IP_PORT;

	@PostMapping("/add/{RCP_SEQ}/{mnum}")
	public String add(@ModelAttribute Ate ate, @PathVariable String RCP_SEQ, 
					@PathVariable int mnum,@RequestParam("file") MultipartFile file) 
			throws Exception {
		
		try {
			
		File fileTest = new File(uploadPath);
		
		if(!fileTest.exists()) {
			
			fileTest.mkdirs();
		}
		
		ate.setRCP_SEQ(RCP_SEQ);
		ate.setMnum(mnum);

		String savedName = file.getOriginalFilename();
		savedName = uploadFile(savedName, file.getBytes());

		ate.setAte_picture(ip + "image/ate/" + savedName);

		String str = "";
		int i = ateService.add(ate);

		if (i > 0) {
			
			str = ate.getAte_num() + "이 등록되었습니다.";
			
		} else {
			
			str = "글 등록에 실패하였습니다. ";
			
		}

		return str;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {

		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		File target = new File(uploadPath, savedName);

		FileCopyUtils.copy(fileData, target);

		return savedName;
	}

	@GetMapping("/get")
	public List<Ate> get() {
		File fileTest = new File(uploadPath);

		if (!fileTest.exists()) {
			fileTest.mkdirs();
		}
		
		return ateService.get();
	}

	@GetMapping("/get/{ate_num}/{mnum}")
	public Map<String, Object> getOne(@PathVariable int ate_num, @PathVariable int mnum) {
		
		File fileTest = new File(uploadPath);

		if (!fileTest.exists()) {
			fileTest.mkdirs();
		}
		
		Map<String, Object> resultMap = new HashMap<>();

		String str = "";

		ateService.upHit(ate_num);

		if (ateService.ateLike(mnum, ate_num) == 1) {

			str = "liked";
		}

		Map<String, Object> result = ateService.getOne(ate_num);
		List<DishComm> commList = ateService.commGet(ate_num);

		resultMap.put("commList", commList);

		resultMap.put("result", result);
		resultMap.put("liked", str);

		return resultMap;
	}

	@PutMapping("/edit/{ate_num}/{mnum}")
	public String edit(@ModelAttribute Ate ate, @RequestParam("file") MultipartFile file) 
			throws Exception {

		File fileTest = new File(uploadPath);
		
		if(!fileTest.exists()) {
			
			fileTest.mkdirs();
		}
		
		String str = "";

		String savedName = file.getOriginalFilename();
		savedName = uploadFile(savedName, file.getBytes());

		ate.setAte_picture(ip + "image/ate/" + savedName);

		int i = ateService.editAte(ate);

		if (i > 0) {
			
			str = "글 수정되었습니다.";
			
		} else {
			
			str = "글 수정에 실패하였습니다.";
			
		}

		return str;
	}

	@GetMapping("/search")
	public List<Map<String, Object>> search(@RequestParam String select) {

		List<Map<String, Object>> ateList = ateService.search(select);

		return ateList;
	}


	@DeleteMapping("/delete/{ate_num}/{mnum}")
	public String delete(@PathVariable int ate_num, @PathVariable int mnum) {

		String str = "";

		int i = ateService.delete(ate_num, mnum);

		if (i > 0) {
			
			str = "글이 삭제되었습니다.";
			
		} else {
			
			str = "글 삭제에 실패하였습니다.";
			
		}

		return str;

	}

	@PostMapping("/like/{ate_num}/{mnum}")
	public String goAteLike(@PathVariable int ate_num, @PathVariable int mnum) {

		String str = "";

		if (ateService.ateLike(mnum, ate_num) == 0) {

			ateService.goAteLike(ate_num, mnum);

			str = "먹음 좋아요 등록!";
			
		} else if (ateService.ateLike(mnum, ate_num) == 1) {

			ateService.goAteDislike(ate_num, mnum);

			str = "먹음 좋아요 해제!";

		}

		return str;
	}

	@PostMapping("/comm/add/{mnum}/{ate_num}")
	public String commAdd(@ModelAttribute DishComm dishComm, @PathVariable int mnum, 
			@PathVariable int ate_num) {

		dishComm.setMnum(mnum);
		dishComm.setAte_num(ate_num);

		String str = "";
		int i = ateService.commAdd(dishComm);

		if (i > 0) {
			
			str = Integer.toString(dishComm.getAc_num());
			
		} else {
			
			str = "댓글 등록에 실패하였습니다.";
		}

		return str;
	}

	@DeleteMapping("/comm/delete/{mnum}/{ac_num}/{ate_num}")
	public String commDelete(@ModelAttribute DishComm dishComm, @PathVariable int mnum,
							@PathVariable int ac_num, @PathVariable int ate_num) {

		dishComm.setMnum(mnum);
		dishComm.setAc_num(ac_num);
		dishComm.setAte_num(ate_num);

		String str = "";
		int i = ateService.commDelete(dishComm);

		if (i > 0) {
			
			str = "댓글이 삭제되었습니다.";
			
		} else {
			
			str = "댓글 삭제에 실패하였습니다.";
			
		}

		return str;
	}

	@PutMapping("/comm/edit/{mnum}/{ac_num}")
	public String commEdit(@ModelAttribute DishComm dishComm, @PathVariable int mnum, 
			@PathVariable int ac_num) {

		dishComm.setMnum(mnum);
		dishComm.setAc_num(ac_num);

		String str = "";
		int i = ateService.commEdit(dishComm);

		if (i > 0) {
			
			str = "댓글이 수정되었습니다.";
			
		} else {
			
			str = "댓글 수정에 실패하였습니다.";
			
		}

		return str;
	}

}
