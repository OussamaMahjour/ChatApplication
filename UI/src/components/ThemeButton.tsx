import { useEffect, useRef } from "react";
import gsap from "gsap";
import { useTheme } from "../provider/ThemeProvider";


const ThemeButton = ({className}:{className?:string})=>{
    const {theme,toggleTheme} = useTheme();

    const toggleRef = useRef<HTMLDivElement | null>(null);
    
    
     useEffect(() => {
            if (!toggleRef.current) return;

            const container = toggleRef.current.parentElement;
            const containerWidth = container?.offsetWidth || 0;
            const circleWidth = toggleRef.current.offsetWidth;

            const maxTranslateX = containerWidth - circleWidth;

            // Position based on current theme
            gsap.set(toggleRef.current, {right:0,
            x: theme !== "light" ? 0 : -maxTranslateX+2,
            });
        }, []);


    const handlClick = () =>{
        if(!toggleRef.current)return;
        const parent_container = toggleRef.current.parentElement;
        if(!parent_container)return;
        const containerWidth = parent_container.offsetWidth;
        const circleWidth = toggleRef.current.offsetWidth;

        // Calculate how far it can move (right edge - left edge)
        const maxTranslateX = containerWidth - circleWidth;

        gsap.set(toggleRef.current,{right:0})
        gsap.to(toggleRef.current, {
        x: theme === "light" ? 0 : -maxTranslateX+2,
        duration: 0.3,
        ease: "power2.out",
        onComplete:toggleTheme
        });
            
    }
    
    
    return <button onClick={handlClick}
         className={`aspect-square w-12  cursor-pointer flex justify-center items-center  ${className}`}>
            <div 
            className="
            w-full p-1
           
            rounded-full border border-background-dark dark:border-background-light
            bg-background-light
            dark:bg-background-dark
            flex justify-between items-center
            relative
            ">
                <div ref={toggleRef} className={`
                h-full aspect-square rounded-full
                bg-background-dark dark:bg-background-light
                border
                border-border-light
                dark:border-border-dark
                translate-x-0
                absolute `}></div>
                <i className={`fa-regular fa-moon text-text-dark`}></i>
                <i className={`fa-solid fa-sun text-text-light`}></i>
            </div>
            
    </button>
}

export default ThemeButton