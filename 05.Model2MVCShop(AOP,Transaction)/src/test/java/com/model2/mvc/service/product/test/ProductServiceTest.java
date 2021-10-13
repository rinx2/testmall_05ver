package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/context-common.xml",
								"classpath:config/context-aspect.xml",
								"classpath:config/context-mybatis.xml",
								"classpath:config/context-transaction.xml"})
public class ProductServiceTest {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProdName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("20210802");
		product.setPrice(10000);
		product.setFileName("testProdFileName");
		
		productService.addProduct(product);
		
		product = productService.getProduct(productService.getLatestProdNo());
		
		Assert.assertEquals(productService.getLatestProdNo(), product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210802", product.getManuDate());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("testProdFileName", product.getFileName());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		int prodNo = productService.getLatestProdNo();
		product = productService.getProduct(prodNo);
		
		Assert.assertEquals(prodNo, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210802", product.getManuDate());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("testProdFileName", product.getFileName());
		
		Assert.assertNotNull(productService.getProduct(10005));
		Assert.assertNotNull(productService.getProduct(10024));
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
		
		Product product = productService.getProduct(productService.getLatestProdNo());
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("testProdFileName", product.getFileName());
		
		product.setProdName("changeProdName");
		product.setProdDetail("changeProdDetail");
		product.setPrice(5000);
		product.setFileName("changeProdFileName");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(productService.getLatestProdNo());
		Assert.assertNotNull(product);
		
		Assert.assertEquals("changeProdName", product.getProdName());
		Assert.assertEquals("changeProdDetail", product.getProdDetail());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("changeProdFileName", product.getFileName());
	}
	
	@Test
	public void testGetProductListAll() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		System.out.println("list: "+list);
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	System.out.println("list: "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	@Test
	public void testGetProductListByProdNo() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("10003");
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());
		
		System.out.println("list: "+list);
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10758");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	System.out.println("list: "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);		
	}
	
	@Test	
	public void testGetProductListByProdName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("연꽃");
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());
		
		System.out.println("list: "+list);
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("결명자차");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	System.out.println("list: "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);		
	}
	
	@Test
	public void testGetProductListByPrice() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("2");
		search.setSearchKeyword("1800");
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());
		
		System.out.println("list: "+list);
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("100");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	System.out.println("list: "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);		
	}

}
