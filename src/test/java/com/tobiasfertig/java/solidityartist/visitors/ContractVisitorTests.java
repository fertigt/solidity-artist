package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContractVisitorTests
{
	private Visitor visitor;

	@Before
	public void setUp( )
	{
		this.visitor = new ContractVisitor( );
	}

	@Test
	public void testVisitDataTypeElement_WithName_CorrectStringReturned( )
	{
		DataTypeElement dataTypeElement = DataTypeElement.STRING;

		dataTypeElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string", this.visitor.export() );
	}

	@Test
	public void testVisitEnumElement_With4Values_CorrectStringReturned( )
	{
		EnumElement enumElement = EnumElement.builder( "Actions" )
											 .addValue( "GoLeft" )
											 .addValue( "GoRight" )
											 .addValue( "GoStraight" )
											 .addValue( "SitStill" )
											 .build( );

		enumElement.accept( this.visitor );

		String expected = "enum Actions {\n" +
						  "    GoLeft,\n" +
					      "    GoRight,\n" +
			              "    GoStraight,\n" +
			              "    SitStill\n" +
			              "}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEnumElement_With1Value_CorrectStringReturned( )
	{
		EnumElement enumElement = EnumElement.builder( "Actions" )
											 .addValue( "GoLeft" )
											 .build( );

		enumElement.accept( this.visitor );

		String expected = "enum Actions {\n" +
			"    GoLeft\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithNameBeforeMemory_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory test", this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithNameAfterMemory_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory test", this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory", this.visitor.export( ) );
	}

	@Test
	public void testVisitParameterElement_WithName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "address test", this.visitor.export( ) );
	}

	@Test
	public void testVisitParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "address", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithNameBeforeStorage_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inStorage( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage test", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithNameAfterStorage_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inStorage( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage test", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inStorage( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage", this.visitor.export( ) );
	}

	@Test
	public void testVisitStructElement_With1Member_CorrectStringReturned( )
	{
		ParameterElement addr = ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "addr" ).build( );
		ParameterElement amount = ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( );
		StructElement structElement = StructElement.builder( "Finder" )
												   .addStructMember( addr )
												   .addStructMember( amount )
												   .build( );

		structElement.accept( this.visitor );

		String expected = "struct Finder {\n" +
						  "    address addr;\n" +
						  "    uint amount;\n" +
						  "}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testUsingForElement_WithCustomExtensionAndSource_CorrectStringReturned( )
	{
		UsingForElement usingForElement = UsingForElement.builder( "X", "Y" ).build( );

		usingForElement.accept( this.visitor );
		assertEquals( "Should be the same text", "using X for Y;\n", this.visitor.export( ) );
	}
}
