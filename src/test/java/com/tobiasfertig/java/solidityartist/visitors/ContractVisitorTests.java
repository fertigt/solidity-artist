package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ParameterElement;
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
}
