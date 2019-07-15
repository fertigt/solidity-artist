package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.FunctionTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.MappingElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.*;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ConstructorElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.DataLocationParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.EventParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FileVisitor extends VisitorImpl
{
	public FileVisitor( )
	{
	}

	@Override public String export( )
	{
		return sb.toString( );
	}

	@Override public void visit( CodeElement element )
	{

	}

	@Override public void visit( ConstructorElement element )
	{

	}

	@Override public void visit( ContractElement element )
	{
		Visitor contractVisitor = new ContractVisitor( );
		element.accept( contractVisitor );
		sb.append( contractVisitor.export( ) );
	}

	@Override public void visit( DataLocationParameterElement element )
	{

	}

	@Override public void visit( DataTypeElement element )
	{

	}

	@Override public void visit( EnumElement element )
	{

	}

	@Override public void visit( EventElement element )
	{

	}

	@Override public void visit( EventParameterElement element )
	{

	}

	@Override public void visit( FileElement element )
	{
		if ( !element.getPragmaDirectives( ).isEmpty( ) )
		{
			for ( PragmaElement pragmaDirective : element.getPragmaDirectives( ) )
			{
				pragmaDirective.accept( this );
				newline( );
			}

			newline( );
		}

		if ( !element.getImportStatements( ).isEmpty( ) )
		{
			for ( ImportElement importStatement : element.getImportStatements( ) )
			{
				importStatement.accept( this );
				newline( );
			}

			newline( );
		}

		boolean hasInterfaces = !element.getInterfaces( ).isEmpty( );
		boolean hasLibraries = !element.getLibraries( ).isEmpty( );
		boolean hasContracts = !element.getContracts( ).isEmpty( );

		if ( hasInterfaces )
		{
			appendCollectionOfSolidityElements( element.getInterfaces( ), "\n\n\n" );
		}

		if ( hasLibraries )
		{
			if ( hasInterfaces )
			{
				newline( );
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getLibraries( ), "\n\n\n" );
		}

		if ( hasContracts )
		{
			if ( hasInterfaces || hasLibraries )
			{
				newline( );
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getContracts( ), "\n\n\n" );
		}
	}

	@Override public void visit( FunctionElement element )
	{

	}

	@Override public void visit( FunctionTypeElement element )
	{

	}

	@Override public void visit( ImportElement element )
	{
		sb.append( Keyword.IMPORT );
		space( );

		if ( !element.getSymbolMap( ).isEmpty( ) )
		{
			if ( element.getSymbolMap( ).containsKey( ImportElement.ASTERISK ) )
			{
				appendAsteriskImport( element.getSymbolMap( ) );
			}
			else
			{
				appendSymbolMap( element.getSymbolMap( ) );
			}

			space( );
			sb.append( Keyword.FROM );
			space( );
		}

		sb.append( "\"" )
		  .append( element.getFileName( ) )
		  .append( "\"" );

		if ( !element.getFileNameAlias( ).isEmpty( ) )
		{
			space( );
			sb.append( Keyword.AS );
			space( );
			sb.append( element.getFileNameAlias( ) );
		}

		semicolon( );
	}

	private void appendAsteriskImport( Map<String,String> symbolMap )
	{
		sb.append( ImportElement.ASTERISK );
		space( );
		sb.append( Keyword.AS );
		space( );
		sb.append( symbolMap.get( ImportElement.ASTERISK ) );
	}

	private void appendSymbolMap( Map<String,String> symbolMap )
	{
		Set<Map.Entry<String,String>> symbolEntries = symbolMap.entrySet( );

		if ( symbolEntries.size( ) > 1 )
		{
			sb.append( "{" );
		}

		Iterator<Map.Entry<String,String>> iterator = symbolEntries.iterator( );
		Map.Entry<String, String> nextEntry = iterator.next( );
		while ( iterator.hasNext( ) )
		{
			if ( nextEntry.getValue( ).isEmpty( ) )
			{
				sb.append( nextEntry.getKey( ) )
				  .append( ";" );
				space( );
			}
			else
			{
				sb.append( nextEntry.getKey( ) );
				space( );
				sb.append( Keyword.AS );
				space( );
				sb.append( nextEntry.getValue( ) )
				  .append( "," );
				space( );
			}

			nextEntry = iterator.next( );
		}

		if ( nextEntry.getValue( ).isEmpty( ) )
		{
			sb.append( nextEntry.getKey( ) );
		}
		else
		{
			sb.append( nextEntry.getKey( ) );
			space( );
			sb.append( Keyword.AS );
			space( );
			sb.append( nextEntry.getValue( ) );
		}

		if ( symbolEntries.size( ) > 1 )
		{
			sb.append( "}" );

		}
	}

	@Override public void visit( InterfaceElement element )
	{
		Visitor interfaceVisitor = new InterfaceVisitor( );
		element.accept( interfaceVisitor );
		sb.append( interfaceVisitor.export( ) );
	}

	@Override public void visit( LibraryElement element )
	{
		Visitor libraryVisitor = new LibraryVisitor( );
		element.accept( libraryVisitor );
		sb.append( libraryVisitor.export( ) );
	}

	@Override public void visit( MappingElement element )
	{

	}

	@Override public void visit( ModifierElement element )
	{

	}

	@Override public void visit( ParameterElement element )
	{

	}

	@Override public void visit( PragmaElement element )
	{
		sb.append( Keyword.PRAGMA );
		space( );
		sb.append( element.getPragmaType( ) );
		space( );
		sb.append( element.getContent( ) );
		semicolon( );
	}

	@Override public void visit( StateVariableElement element )
	{

	}

	@Override public void visit( StructElement element )
	{

	}

	@Override public void visit( UsingForElement element )
	{

	}
}
