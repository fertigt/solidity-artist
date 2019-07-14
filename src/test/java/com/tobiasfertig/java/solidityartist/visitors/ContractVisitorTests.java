package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
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
	public void testVisitCodeElement_With4Line_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addCode( "if (!self.flags[value]) {" )
									  .addStatement( "    return false" )
									  .addStatement( "    self.flags[value] = false" )
									  .addStatement( "    return true" )
									  .addCode( "}" )
									  .build( );

		code.accept( this.visitor );

		String expected = "if (!self.flags[value]) {\n" +
			"    return false;\n" +
			"    self.flags[value] = false;\n" +
			"    return true;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitCodeElement_With1Line_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "self.flags[value] = false" )
									  .build( );

		code.accept( this.visitor );

		String expected = "self.flags[value] = false;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitCodeElement_WithoutLines_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).build( );
		code.accept( this.visitor );
		assertEquals( "Should be the same text", "", this.visitor.export( ) );
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
	public void testVisitEventElement_With3Parameter_CorrectStringReturned( )
	{
		ParameterElement from = ParameterElement.builder( DataTypeElement.ADDRESS )
												.isIndexedEventParameter( )
												.addName( "from" )
												.build( );

		ParameterElement id = ParameterElement.builder( DataTypeElement.BYTES32 )
												.isIndexedEventParameter( )
												.addName( "id" )
												.build( );

		ParameterElement value = ParameterElement.builder( DataTypeElement.UINT )
												.addName( "value" )
												.build( );

		EventElement event = EventElement.builder( "Deposit" )
										 .addParameter( from )
										 .addParameter( id )
										 .addParameter( value )
										 .build( );

		event.accept( this.visitor );

		String expected = "event Deposit(address indexed from, bytes32 indexed id, uint value);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEventElement_With1Parameter_CorrectStringReturned( )
	{
		ParameterElement from = ParameterElement.builder( DataTypeElement.ADDRESS )
												.isIndexedEventParameter( )
												.addName( "from" )
												.build( );

		EventElement event = EventElement.builder( "Deposit" )
										 .addParameter( from )
										 .build( );

		event.accept( this.visitor );

		String expected = "event Deposit(address indexed from);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameBeforeAnonymous_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.isAnonymousEventParameter( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string anonymous test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameAfterAnonymous_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.isAnonymousEventParameter( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string anonymous test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameBeforeIndexed_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.isIndexedEventParameter( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string indexed test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameAfterIndexed_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.isIndexedEventParameter( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string indexed test", this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FallbackFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).addStatement( "x = 1" ).build( );
		FunctionElement fallback = FunctionElement.fallbackBuilder( ).addCode( code ).build( );

		fallback.accept( this.visitor );

		String expected = "function() external {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FallbackFunctionPayable_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.fallbackBuilder( )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function() external payable {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PureFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "pureFunction" )
												  .isPure( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function pureFunction() public pure {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_ViewFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "viewFunction" )
												  .isView( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function viewFunction() public view {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_AbstractFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "abstractFunction" )
												  .isAbstract( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function abstractFunction() public;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PayableFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() public payable {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction(string memory test) public payable {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction(string memory test, address test2) public payable {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1ReturnParameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory test) {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2ReturnParameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory test, address test2) {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1UnnamedReturnParameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory) {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2UnnamedReturnParameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory, address) {\n" +
			"    x = 1;\n" +
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
	public void testVisitModifierElement_WithoutCodeAndParameters_CorrectStringReturned( )
	{
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAndWithoutParametersAndUnderscore_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(owner = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    require(owner = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAndWithoutParameters_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(owner = msg.sender)" )
									  .addStatement( "_" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addCode( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    require(owner = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAnd1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(test = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addParameter( parameterElement )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner(address test) {\n" +
			"    require(test = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAnd2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test2" )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(test = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner(address test, address test2) {\n" +
			"    require(test = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
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
	public void testVisitStateVariableElement_WithNamePublic_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isPublic( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address public owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNamePrivate_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isPrivate( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address private owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameExternal_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isExternal( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address external owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameInternal_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isInternal( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address internal owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_AsConstant_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isPrivate( )
																 .isConstant()
																 .addInitialization( "5" )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "uint private constant amount = 5;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameAndInitialization_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isInternal( )
																 .addInitialization( "5" )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "uint internal amount = 5;", this.visitor.export( ) );
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
	public void testVisitUsingForElement_WithCustomExtensionAndSource_CorrectStringReturned( )
	{
		UsingForElement usingForElement = UsingForElement.builder( "X", "Y" ).build( );

		usingForElement.accept( this.visitor );
		assertEquals( "Should be the same text", "using X for Y;", this.visitor.export( ) );
	}
}
