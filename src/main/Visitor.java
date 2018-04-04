package main;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.*;

//Modified ASTVisitor
/**
 * AST Visitor for visiting References & Declarations
 * count[1] is declarations
 * count[0] is references
 * Contains total count of nested/local/anon type declarations
 */
public class Visitor extends ASTVisitor{
	Map<String, Integer[]> map = new HashMap<String, Integer[]>();
	int nestCount = 0;
	int localCount = 0;
	int anonCount = 0;
	int otherCount=0;
	
	public int getOtherCount() {
		return otherCount;
	}
	public int getNestCount() {
		return nestCount;
	}
	public int getLocalCount() {
		return localCount;
	}
	public int getAnonCount() {
		return anonCount;
	}
	
	
	public Map<String, Integer[]> getMap(){
		return map;
	}
	
	//Visits when there is a primitive type (int, char, ...)
	@Override
	public boolean visit(PrimitiveType node) {			
		if(!node.toString().equals("void") && node.resolveBinding() != null) {
			String key = node.resolveBinding().getQualifiedName(); 
			Integer[] count = map.get(key);
			if(count != null) 
				count[0]++;
			else
				count = new Integer[] {1,0};
			map.put(key, count);
		}
		return super.visit(node);
	}
	
	//Visits when there is a SimpleType type (non-Primitive types like java.lang.String)
	@Override
	public boolean visit(SimpleType node) {
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().isParameterizedType()) { // handle parameterized type in its own visit method below
				return super.visit(node); 
			}
			String key = node.resolveBinding().getQualifiedName();
			if (key.equals(""))
				key = node.resolveBinding().getName();
			Integer[] count = map.get(key); 
			if(count != null) 
				count[0]++;
			else
				count = new Integer[] {1,0};
			map.put(key, count);
		}
		return super.visit(node);
	}
	
	//1. AnnotationType declaration
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().isNested()) {
				nestCount++;
			}
			if (node.resolveBinding().isLocal()) {
				localCount++;
			}
			if (node.resolveBinding().isAnonymous()) {
				anonCount++;
			}
			if(!((node.resolveBinding().isLocal())) && !(node.resolveBinding().isNested()) &&! (node.resolveBinding().isAnonymous())){
				otherCount++;
			}
				
			String key = node.resolveBinding().getQualifiedName();
			if (key.equals(""))
				key = node.resolveBinding().getName();
			Integer[] count = map.get(key);
			if(count != null) 
				count[1]++;
			else
				count = new Integer[] {0,1};
			map.put(key, count);	
		}
		return super.visit(node);
	}
	
	//2. Enum declaration
	@Override
	public boolean visit(EnumDeclaration node) {
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().isNested()) {
				nestCount++;
			}
			if (node.resolveBinding().isLocal()) {
				localCount++;
			}
			if (node.resolveBinding().isAnonymous()) {
				anonCount++;
			}
			if(!((node.resolveBinding().isLocal())) && !(node.resolveBinding().isNested()) &&! (node.resolveBinding().isAnonymous())){
				otherCount++;
			}
			
			String key = node.resolveBinding().getQualifiedName();
			if (key.equals(""))
				key = node.resolveBinding().getName();
			Integer[] count = map.get(key);
			if(count != null) 
				count[1]++;
			else
				count = new Integer[] {0,1};
			map.put(key, count); 
		}
		return super.visit(node);
	}
	
	//3-4. Class / Interface declaration
	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().isNested()) {
				nestCount++;
			}
			if (node.resolveBinding().isLocal()) {
				localCount++;
			}
			if(!((node.resolveBinding().isLocal())) && !(node.resolveBinding().isNested()) &&! (node.resolveBinding().isAnonymous())){
				otherCount++;
			}
			
			
			String key = node.resolveBinding().getQualifiedName();
			if (key.equals(""))
				key = node.resolveBinding().getName();
			Integer[] count = map.get(key);
			if(count != null) 
				count[1]++;
			else
				count = new Integer[] {0,1};
			map.put(key, count); 
		}
		return super.visit(node);
	}
	
	// 5. Anonymous Class declaration
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().isAnonymous()) {
				anonCount++;
				String key = node.resolveBinding().getQualifiedName();
				if (key.equals(""))
					key = node.resolveBinding().getKey() + " (Anonymous Class)";  
				Integer[] count = map.get(key);
				if(count != null) 
					count[1]++;
				else
					count = new Integer[] {0,1};
				map.put(key, count); 
			}
		}
		return super.visit(node);
	}
	
	//Import Statement
	@Override
	public boolean visit(ImportDeclaration node) {
		if (node.resolveBinding() != null) {
			if (!node.isOnDemand()) { // i.e. not of the form package.*; 
				String key = node.getName().toString();  
				Integer[] count = map.get(key);
				if(count != null) 
					count[0]++;
				else
					count = new Integer[] {1,0};
				map.put(key, count);
			}
		}
		return super.visit(node);
	}
	
	//Constructor declaration is a reference to its class
	@Override
	public boolean visit(MethodDeclaration node) {
		if (node.resolveBinding() != null) {
			if (node.isConstructor()) {
				String key = node.resolveBinding().getDeclaringClass().getQualifiedName();
				if (key.equals(""))
					key = node.resolveBinding().getDeclaringClass().getName(); 
				Integer[] count = map.get(key);
				if(count != null) 
					count[0]++; // increment reference count
				else
					count = new Integer[] {1,0};
				map.put(key, count); 
			}
		}
		return super.visit(node);
	}	
	
	@Override
	public boolean visit(ParameterizedType node) {
		if (node.resolveBinding() != null) {
			String key = node.resolveBinding().getTypeDeclaration().getQualifiedName(); 
			if (key.equals(""))
				key = node.resolveBinding().getTypeDeclaration().getName(); 
			Integer[] count = map.get(key);
			if(count != null) 
				count[0]++; // increment reference count
			else
				count = new Integer[] {1,0};
			map.put(key, count);
		}
		return super.visit(node);
	}	

	@Override
	public boolean visit(ArrayType node) {
		if (node.resolveBinding() != null) {
			String key; 
			if (node.resolveBinding().getElementType().isLocal()) {
				key = node.resolveBinding().getElementType().getName(); // name without brackets
			}
			else if (node.resolveBinding().getElementType().isParameterizedType()) {
				key = node.resolveBinding().getElementType().getTypeDeclaration().getQualifiedName(); // name without brackets
				if (key.equals(""))
					key = node.resolveBinding().getElementType().getTypeDeclaration().getName(); // name without brackets 
			}
			else {
				key = node.resolveBinding().getElementType().getQualifiedName(); // name without brackets 
				if (key.equals(""))
					key = node.resolveBinding().getElementType().getName(); // name without brackets 
			}
			int dimensions = node.getDimensions(); 
			for (int i = 0; i < dimensions; i++) {
				key += "[]";
				Integer[] count = map.get(key);
				if(count != null) 
					count[0]++; // increment reference count
				else
					count = new Integer[] {1,0};
				map.put(key, count);
			}
		}
		return super.visit(node);
	}

	
}
