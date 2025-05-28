import { ReactNode } from "react";
import FormInput from "../components/FormInput";
import Card from "../components/Card";
import Button from "../components/Button";
import ButtonInverse from "../components/ButtonInverse";
import ThemeButton from "../components/ThemeButton";
import { useTheme } from "../provider/ThemeProvider";

const Test = ():ReactNode=>{

    const {setAlert} = useTheme()

    return <div className="grid grid-flow-row gap-4  grid-cols-4 grid-rows-4  h-screen w-screen p-4 overflow-y-auto relative">
            <Card className=" w-fit h-fit absolute bottom-3 right-0 ">
                <ThemeButton />
             </Card> 
            <Card className="p-2 w-full h-fit  ">
                <FormInput placeholder="Form Input" id="test" type="text" className="w-full " />
             </Card>
             <Card className="p-2 w-full h-fit  ">
                <FormInput placeholder="Form Input" id="test" type="text" className="w-full invalid " errorMessage="Invalid Form Input" />
             </Card>
             <Card className="p-2 w-full h-fit">
               <Button className="w-full">
                    Button
               </Button>
             </Card>
             <Card className="p-2 w-full h-fit">
               <ButtonInverse onClick={()=>setAlert({message:"This is an Alert message",timeout:1000})} className="w-full">
                    ButtonInverse
               </ButtonInverse>
             </Card> 
            
                           
    </div>
}

export default Test;