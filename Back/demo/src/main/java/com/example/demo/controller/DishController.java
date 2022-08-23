package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.Service.DishService;
import com.example.demo.payload.UploadFileResponse;
import com.example.demo.vo.Dish;

@RestController
@RequestMapping("/api/dish")
public class DishController {
	
	@Autowired
	DishService dishService;

	
	/*
	 * 음식 추가
	 */
	@PostMapping("/add")
	public String add(@RequestBody Dish dish) {

		//사진 추가
		//pictureTest(file);
		dishService.add(dish);
		
		for(int i = 0 ; i < dish.getPictures().size(); i++) {
			System.out.println("dish.getPictures()::: " + dish.getPictures().get(i));
			//dish.getPictures().get(i);
			Map<String, Object> param = new HashMap<>();
			//param.put("dnum", param);
			String pname = dish.getPictures().get(i);
			param.put("pname", pname);
			dishService.addPicture(param);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(pname)
	                .toUriString();
			
			//Path targetLocation = this.fileStorageLocation.resolve(pname);
            //Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            //Files.copy(file.getInputStream(), "C:\\Temp\\엘라스틱 서치.PNG", StandardCopyOption.REPLACE_EXISTING);
            
			//UploadFileResponse uploadFileResponse = new UploadFileResponse(pname, fileDownloadUri, fileDownloadUri, i);
			
			//이미지 주소 		
			//String imagePath = fileDownloadUri;// C:/upload/test.png
			String imagePath = "C:\\upload\\"+pname;
			//버퍼이미지 변수 정의		BufferedImage image = null;				
			try {			
				//버퍼이미지에 경로에 이미지를 읽어서 넣음			
				BufferedImage image = ImageIO.read(new URL(imagePath));			
				//이미지주소의 맨끝부분에 파일이름을 자름			
				String fileNm = pname;			
				//저장할 디렉터리와파일명 생성			
				File file = new File("C:/Temp/"+fileNm);			
				//해당경로로 gif형식의 이미지파일을 저장			
				ImageIO.write(image, "png", file);
				
				//System.out.println("fileDownloadUri::: " + fileDownloadUri);
				//fileDownloadUri::: http://localhost:8080/downloadFile/test.png
				new UploadFileResponse(pname, fileDownloadUri,"image/png", 87314);
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		

		return dish.getTitle() + "이 등록되었습니다.";
	}

	

	/*
	 * 음식 전체 리스트 불러오기
	 */
	@GetMapping("/get")
	public List<Dish> get() {

		return dishService.get();
	}
	
	/*
	 * 음식 정보 한개 불러오기
	 */
	@GetMapping("/get/{dnum}")
	public Dish getOne(@PathVariable int dnum) {
		/*
		 * 조회수 +1 
		 */
		dishService.upHit(dnum);
		/*
		 * 아직 테이블 완성 안해서 못함 
		 * 			: 좋아요 테이블 조회해서 로그인한 사람 -> 본 글에 대한 좋아요 눌렀는지 안눌렀는지 체크 
		 */
		
		return dishService.getOne(dnum);
	}
	
	/*
	 * 음식 정보 수정하기
	 * 	오수진(0818) : 추후 프론트와 이야기! 
	 *  							-> id 정보 받을 방법 정해서 수정하기
	 *  								->지금은 id가 없어서 내가 적어줘야하지만, 
	 *  									나중엔 dish Map안에 들어있을 예정
	 *  							-> 기존 정보 어떻게 보여줄지 정하기   
	 */
	@PutMapping("/edit/{dnum}")
	public String edit(@RequestBody Dish dish, @PathVariable int dnum) {
		
		//오수진(0818): 프론트와 상의하기 
			//1. 기존 정보 불러오기 (이건 어떻게 작동해서 보여줄지 상의하자) 
			//Dish BeforeData = dishService.getOne(id);
		
		dish.setDnum(dnum);
		
		dishService.edit(dish);
		
		return dish.getTitle() +"이 수정되었습니다.";
	}
	/*
	 * 음식 정보 삭제하기 
	 */
	@DeleteMapping("/delete/{dnum}")
	public String delete(@PathVariable int dnum) {
		//정말 삭제하시겠습니까? 질문 하는거...(팝업)
		
		dishService.delete(dnum);
		
		return "글이 삭제되었습니다.";
	}
	
	
	

}
