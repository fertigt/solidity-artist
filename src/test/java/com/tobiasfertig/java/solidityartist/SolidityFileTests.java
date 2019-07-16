package com.tobiasfertig.java.solidityartist;

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

import java.io.IOException;

public class SolidityFileTests
{
	private FileElement fileElement;

	@Before
	public void setUp( )
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
												  .addExternalFunction( total )
												  .addPublicFunction( totalSupply )
												  .addInternalFunction( _transfer )
												  .addPrivateFunction( totally )
												  .build( );

		this.fileElement = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addInterface( interfaceElement )
									  .addInterface( interfaceElement1 )
									  .addLibrary( libraryElement )
									  .addLibrary( libraryElement1 )
									  .addContract( contractElement )
									  .addContract( contract )
									  .build( );
	}

	@Test
	public void testSaveToFile_WritesSampleContract_CorrectFileWritten( )
	{
		SolidityFile solidityFile = new SolidityFile( "A.sol", "./contracts", this.fileElement );

		try
		{
			solidityFile.saveToFile( );
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
	}
}
