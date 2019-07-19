package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.*;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ConstructorElement;
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

public class FileVisitorTests
{
	private Visitor visitor;

	@Before
	public void setUp( )
	{
		this.visitor = new FileVisitor( 99 );
	}

	@Test
	public void testVisitContractElement_Complete_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );

		StateVariableElement _totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
																.isPrivate( )
																.build( );

		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );

		FunctionElement fallbackFunction =
			FunctionElement.fallbackBuilder( )
						   .addCode( CodeElement.builder( ).addStatement( "owner = msg.sender" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .addCode( CodeElement.builder( )
																	.addStatement( "return _totalSupply" )
																	.build( )
											   )
											   .build( );

		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );

		FunctionElement _transfer = FunctionElement.internalBuilder( "_transfer" )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "sender" )
																				  .build( ) )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "recipient" )
																				  .build( )
												   )
												   .addParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																				  .addName( "amount" )
																				  .build( )
												   )
												   .addCode( CodeElement.builder( )
																		.addStatement(
																			"require(sender != address(0), \"ERC20: transfer from the zero address\")" )
																		.addStatement(
																			"require(recipient != address(0), \"ERC20: transfer to the zero address\")" )
																		.addStatement( "_balances[sender] = " +
																			"_balances[sender].sub(amount)" )
																		.addStatement(
																			"_balances[recipient] = _balances[recipient].add(amount)" )
																		.addStatement(
																			"emit Transfer(sender, recipient, amount)" )
																		.build( )
												   )
												   .build( );

		FunctionElement totally = FunctionElement.privateBuilder( "totally" )
												 .isView( )
												 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					  .build( )
												 )
												 .addCode( CodeElement.builder( )
																	  .addStatement( "return _totalSupply" )
																	  .build( )
												 )
												 .build( );

		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addEnumDeclaration( enumElement )
												  .addStructDeclaration( struct )
												  .addStateVariable( _totalSupply )
												  .addEventDeclaration( event )
												  .addModifierDeclaration( modifier )
												  .addConstructor( constructor )
												  .addFallbackFunction( fallbackFunction )
												  .addFunction( total )
												  .addFunction( totalSupply )
												  .addFunction( _transfer )
												  .addFunction( totally )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 is IERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    function() external {\n" +
			"        owner = msg.sender;\n" +
			"    }\n" +
			"\n" +
			"    function total() external view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function _transfer(address sender, address recipient, uint256 amount) internal {\n" +
			"        require(sender != address(0), \"ERC20: transfer from the zero address\");\n" +
			"        require(recipient != address(0), \"ERC20: transfer to the zero address\");\n" +
			"        _balances[sender] = _balances[sender].sub(amount);\n" +
			"        _balances[recipient] = _balances[recipient].add(amount);\n" +
			"        emit Transfer(sender, recipient, amount);\n" +
			"    }\n" +
			"\n" +
			"    function totally() private view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFileElement_SingleContract_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );

		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		StateVariableElement _totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
																.isPrivate( )
																.build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addStateVariable( _totalSupply )
												  .addConstructor( constructor )
												  .addFunction( totalSupply )
												  .build( );

		FileElement file = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addContract( contract )
									  .build( );

		file.accept( this.visitor );
		String expected = "pragma solidity ^0.5.10;\n" +
			"\n" +
			"import \"./IERC20.sol\";\n" +
			"\n" +
			"contract ERC20 is IERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFileElement_SingleInterface_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );

		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );
		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		FunctionElement fallbackFunction =
			FunctionElement.fallbackBuilder( )
						   .addCode( CodeElement.builder( ).addStatement( "owner = msg.sender" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .build( );

		InterfaceElement interfaceElement = InterfaceElement.builder( "Test" )
															.addUsingForDeclaration( usingFor )
															.addEnumDeclaration( enumElement )
															.addStructDeclaration( struct )
															.addEventDeclaration( event )
															.addModifierDeclaration( modifier )
															.addFallbackFunction( fallbackFunction )
															.addExternalFunction( total )
															.build( );

		FileElement file = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addInterface( interfaceElement )
									  .build( );

		file.accept( this.visitor );
		String expected = "pragma solidity ^0.5.10;\n" +
			"\n" +
			"import \"./IERC20.sol\";\n" +
			"\n" +
			"interface Test {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    function() external;\n" +
			"\n" +
			"    function total() external view returns(uint256);\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFileElement_SingleLibrary_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );

		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );

		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isPrivate( )
																 .isConstant()
																 .addInitialization( "5" )
																 .build( );
		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .addCode( CodeElement.builder( )
																	.addStatement( "return _totalSupply" )
																	.build( )
											   )
											   .build( );

		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );

		FunctionElement _transfer = FunctionElement.internalBuilder( "_transfer" )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "sender" )
																				  .build( ) )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "recipient" )
																				  .build( )
												   )
												   .addParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																				  .addName( "amount" )
																				  .build( )
												   )
												   .addCode( CodeElement.builder( )
																		.addStatement(
																			"require(sender != address(0), \"ERC20: transfer from the zero address\")" )
																		.addStatement(
																			"require(recipient != address(0), \"ERC20: transfer to the zero address\")" )
																		.addStatement( "_balances[sender] = " +
																			"_balances[sender].sub(amount)" )
																		.addStatement(
																			"_balances[recipient] = _balances[recipient].add(amount)" )
																		.addStatement(
																			"emit Transfer(sender, recipient, amount)" )
																		.build( )
												   )
												   .build( );

		FunctionElement totally = FunctionElement.privateBuilder( "totally" )
												 .isView( )
												 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					  .build( )
												 )
												 .addCode( CodeElement.builder( )
																	  .addStatement( "return _totalSupply" )
																	  .build( )
												 )
												 .build( );

		LibraryElement libraryElement = LibraryElement.builder( "Test" )
													  .addUsingForDeclaration( usingFor )
													  .addEnumDeclaration( enumElement )
													  .addStructDeclaration( struct )
													  .addConstantStateVariable( stateVariable )
													  .addEventDeclaration( event )
													  .addModifierDeclaration( modifier )
													  .addFunction( total )
													  .addFunction( totalSupply )
													  .addFunction( _transfer )
													  .addFunction( totally )
													  .build( );

		FileElement file = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addLibrary( libraryElement )
									  .build( );

		file.accept( this.visitor );
		String expected = "pragma solidity ^0.5.10;\n" +
			"\n" +
			"import \"./IERC20.sol\";\n" +
			"\n" +
			"library Test {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    uint private constant amount = 5;\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    function total() external view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function _transfer(address sender, address recipient, uint256 amount) internal {\n" +
			"        require(sender != address(0), \"ERC20: transfer from the zero address\");\n" +
			"        require(recipient != address(0), \"ERC20: transfer to the zero address\");\n" +
			"        _balances[sender] = _balances[sender].sub(amount);\n" +
			"        _balances[recipient] = _balances[recipient].add(amount);\n" +
			"        emit Transfer(sender, recipient, amount);\n" +
			"    }\n" +
			"\n" +
			"    function totally() private view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFileElement_Complete_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );

		InterfaceElement interfaceElement = InterfaceElement.builder( "IA" ).build( );
		InterfaceElement interfaceElement1 = InterfaceElement.builder( "IB" ).build( );

		LibraryElement libraryElement = LibraryElement.builder( "LA" ).build( );
		LibraryElement libraryElement1 = LibraryElement.builder( "LB" ).build( );

		UsingForElement usingFor = UsingForElement.builder( "LA", DataTypeElement.UINT256 ).build( );
		UsingForElement usingFor1 = UsingForElement.builder( "LB", DataTypeElement.ADDRESS ).build( );
		ContractElement contractElement = ContractElement.builder( "A" )
														 .addInheritedContract( "IA" )
														 .addInheritedContract( "IB" )
														 .addUsingForDeclaration( usingFor )
														 .addUsingForDeclaration( usingFor1 )
														 .build( );

		FileElement file = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addInterface( interfaceElement )
									  .addInterface( interfaceElement1 )
									  .addLibrary( libraryElement )
									  .addLibrary( libraryElement1 )
									  .addContract( contractElement )
									  .build( );

		file.accept( this.visitor );
		String expected = "pragma solidity ^0.5.10;\n" +
			"\n" +
			"import \"./IERC20.sol\";\n" +
			"\n" +
			"interface IA {\n" +
			"\n" +
			"}\n" +
			"\n" +
			"\n" +
			"interface IB {\n" +
			"\n" +
			"}\n" +
			"\n" +
			"\n" +
			"library LA {\n" +
			"\n" +
			"}\n" +
			"\n" +
			"\n" +
			"library LB {\n" +
			"\n" +
			"}\n" +
			"\n" +
			"\n" +
			"contract A is IA, IB {\n" +
			"    using LA for uint256;\n" +
			"    using LB for address;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitImportElement_ImportFile_CorrectStringReturned( )
	{
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );
		importElement.accept( this.visitor );
		String expected = "import \"./IERC20.sol\";";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitImportElement_ImportAsterisk_CorrectStringReturned( )
	{
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" )
												   .useSymbolForAll( "symbolName" )
												   .build( );
		importElement.accept( this.visitor );
		String expected = "import * as symbolName from \"./IERC20.sol\";";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitImportElement_ImportFileAsAlias_CorrectStringReturned( )
	{
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" )
												   .addFileNameAlias( "symbolName" )
												   .build( );
		importElement.accept( this.visitor );
		String expected = "import \"./IERC20.sol\" as symbolName;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitImportElement_ImportMultipleSymbolsFromFile_CorrectStringReturned( )
	{
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" )
												   .addSymbolWithAlias( "symbol1", "alias" )
												   .addSymbol( "symbol2" )
												   .build( );
		importElement.accept( this.visitor );
		String expected = "import {symbol1 as alias, symbol2} from \"./IERC20.sol\";";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitInterfaceElement_Complete_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );
		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		FunctionElement fallbackFunction =
			FunctionElement.fallbackBuilder( )
						   .addCode( CodeElement.builder( ).addStatement( "owner = msg.sender" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .build( );

		InterfaceElement interfaceElement = InterfaceElement.builder( "Test" )
															.addUsingForDeclaration( usingFor )
															.addEnumDeclaration( enumElement )
															.addStructDeclaration( struct )
															.addEventDeclaration( event )
															.addModifierDeclaration( modifier )
															.addFallbackFunction( fallbackFunction )
															.addExternalFunction( total )
															.build( );

		interfaceElement.accept( this.visitor );
		String expected = "interface Test {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    function() external;\n" +
			"\n" +
			"    function total() external view returns(uint256);\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitLibraryElement_Complete_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );

		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isPrivate( )
																 .isConstant()
																 .addInitialization( "5" )
																 .build( );
		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .addCode( CodeElement.builder( )
																	.addStatement( "return _totalSupply" )
																	.build( )
											   )
											   .build( );

		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );

		FunctionElement _transfer = FunctionElement.internalBuilder( "_transfer" )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "sender" )
																				  .build( ) )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "recipient" )
																				  .build( )
												   )
												   .addParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																				  .addName( "amount" )
																				  .build( )
												   )
												   .addCode( CodeElement.builder( )
																		.addStatement(
																			"require(sender != address(0), \"ERC20: transfer from the zero address\")" )
																		.addStatement(
																			"require(recipient != address(0), \"ERC20: transfer to the zero address\")" )
																		.addStatement( "_balances[sender] = " +
																			"_balances[sender].sub(amount)" )
																		.addStatement(
																			"_balances[recipient] = _balances[recipient].add(amount)" )
																		.addStatement(
																			"emit Transfer(sender, recipient, amount)" )
																		.build( )
												   )
												   .build( );

		FunctionElement totally = FunctionElement.privateBuilder( "totally" )
												 .isView( )
												 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					  .build( )
												 )
												 .addCode( CodeElement.builder( )
																	  .addStatement( "return _totalSupply" )
																	  .build( )
												 )
												 .build( );

		LibraryElement libraryElement = LibraryElement.builder( "Test" )
													  .addUsingForDeclaration( usingFor )
													  .addEnumDeclaration( enumElement )
													  .addStructDeclaration( struct )
													  .addConstantStateVariable( stateVariable )
													  .addEventDeclaration( event )
													  .addModifierDeclaration( modifier )
													  .addFunction( total )
													  .addFunction( totalSupply )
													  .addFunction( _transfer )
													  .addFunction( totally )
													  .build( );

		libraryElement.accept( this.visitor );
		String expected = "library Test {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    uint private constant amount = 5;\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    function total() external view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function _transfer(address sender, address recipient, uint256 amount) internal {\n" +
			"        require(sender != address(0), \"ERC20: transfer from the zero address\");\n" +
			"        require(recipient != address(0), \"ERC20: transfer to the zero address\");\n" +
			"        _balances[sender] = _balances[sender].sub(amount);\n" +
			"        _balances[recipient] = _balances[recipient].add(amount);\n" +
			"        emit Transfer(sender, recipient, amount);\n" +
			"    }\n" +
			"\n" +
			"    function totally() private view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitPragmaElement_VersionPragma_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		pragma.accept( this.visitor );
		String expected = "pragma solidity ^0.5.10;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitPragmaElement_ExperimentalPragmaAbiEncoderV2_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.experimentalBuilder( ).abiEncoderV2( ).build( );
		pragma.accept( this.visitor );
		String expected = "pragma experimental ABIEncoderV2;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitPragmaElement_ExperimentalPragmaSMTChecker_CorrectStringReturned( )
	{
		PragmaElement pragma = PragmaElement.experimentalBuilder( ).smtChecker( ).build( );
		pragma.accept( this.visitor );
		String expected = "pragma experimental SMTChecker;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}
}
