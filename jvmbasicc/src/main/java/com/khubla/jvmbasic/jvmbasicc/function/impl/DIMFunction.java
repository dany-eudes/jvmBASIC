package com.khubla.jvmbasic.jvmbasicc.function.impl;

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
import org.antlr.runtime.tree.CommonTree;

import com.khubla.jvmbasic.jvmbasicc.compiler.Dispatcher;
import com.khubla.jvmbasic.jvmbasicc.compiler.GenerationContext;
import com.khubla.jvmbasic.jvmbasicc.function.BaseFunction;

/**
 * a DIM in BASIC declares an array. the RT code stores arrays as Hashtables, so we dont actually care how many dimensions there are.
 * 
 * @author tome
 */
public class DIMFunction extends BaseFunction {
   @Override
   public boolean execute(GenerationContext generationContext) throws Exception {
      try {
         /*
          * so the subtree under the <DIM> here will be <varname> <NUMBER> )
          */
         if (generationContext.getCommonTree().getChildCount() == 4) {
            /*
             * get the variable name
             */
            final GenerationContext nameGenerationContext = new GenerationContext(generationContext, (CommonTree) generationContext.getCommonTree().getChild(0));
            Dispatcher.dispatch(nameGenerationContext);
            /*
             * executionContext.setVariable(executionContext.pop().getString(), new Value(new Array()));
             */
            generationContext.getMethodVisitor().visitVarInsn(ALOAD, 0);
            generationContext.getMethodVisitor().visitFieldInsn(GETFIELD, generationContext.getClassName(), "executionContext", "Lcom/khubla/jvmbasic/jvmbasicrt/ExecutionContext;");
            generationContext.getMethodVisitor().visitVarInsn(ALOAD, 0);
            generationContext.getMethodVisitor().visitFieldInsn(GETFIELD, generationContext.getClassName(), "executionContext", "Lcom/khubla/jvmbasic/jvmbasicrt/ExecutionContext;");
            generationContext.getMethodVisitor().visitMethodInsn(INVOKEVIRTUAL, "com/khubla/jvmbasic/jvmbasicrt/ExecutionContext", "pop", "()Lcom/khubla/jvmbasic/jvmbasicrt/Value;");
            generationContext.getMethodVisitor().visitMethodInsn(INVOKEVIRTUAL, "com/khubla/jvmbasic/jvmbasicrt/Value", "getString", "()Ljava/lang/String;");
            generationContext.getMethodVisitor().visitTypeInsn(NEW, "com/khubla/jvmbasic/jvmbasicrt/Value");
            generationContext.getMethodVisitor().visitInsn(DUP);
            generationContext.getMethodVisitor().visitTypeInsn(NEW, "com/khubla/jvmbasic/jvmbasicrt/Array");
            generationContext.getMethodVisitor().visitInsn(DUP);
            generationContext.getMethodVisitor().visitMethodInsn(INVOKESPECIAL, "com/khubla/jvmbasic/jvmbasicrt/Array", "<init>", "()V");
            generationContext.getMethodVisitor().visitMethodInsn(INVOKESPECIAL, "com/khubla/jvmbasic/jvmbasicrt/Value", "<init>", "(Lcom/khubla/jvmbasic/jvmbasicrt/Array;)V");
            generationContext.getMethodVisitor().visitMethodInsn(INVOKEVIRTUAL, "com/khubla/jvmbasic/jvmbasicrt/ExecutionContext", "setVariable",
                  "(Ljava/lang/String;Lcom/khubla/jvmbasic/jvmbasicrt/Value;)V");
         }
         return true;
      } catch (final Exception e) {
         throw new Exception("Exception in execute", e);
      }
   }
}
