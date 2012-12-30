package com.khubla.jvmbasic.jvmbasicc.compiler;

/*
 * jvmBasic Copyright 2012, khubla.com
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
import com.khubla.jvmbasic.jvmbasicc.compiler.analysis.Statement;
import com.khubla.jvmbasic.jvmbasicc.compiler.analysis.StatementProcessor;

/**
 * @author tome
 */
public class DefaultStatementProcessor implements StatementProcessor {
   /**
    * the generation context
    */
   private final GenerationContext generationContext;

   /**
    * ctor
    */
   public DefaultStatementProcessor(GenerationContext generationContext) {
      this.generationContext = generationContext;
   }

   @Override
   public boolean processStatement(Statement statement) throws Exception {
      try {
         /*
          * first statement on the line?
          */
         if (0 == statement.getLineIndex()) {
            /*
             * make the label
             */
            generationContext.getMethodVisitor().visitLabel(statement.getLineDeclaration().getLabel());
            /*
             * save the line number
             */
            generationContext.getMethodVisitor().visitLineNumber(statement.getLineDeclaration().getBasicLine(), statement.getLineDeclaration().getLabel());
         }
         /*
          * process the statement
          */
         final GenerationContext subGenerationContext = new GenerationContext(generationContext, statement.getCommonTree(), statement.getLineDeclaration().getBasicLine());
         return Dispatcher.dispatch(subGenerationContext);
      } catch (final Exception e) {
         throw new Exception("Exception in processStatement", e);
      }
   }
}
