import React, { useEffect, useRef } from 'react';

// inspiration: https://codepen.io/jonathanbell/pen/OvYVYw
const Confetti: React.FC = () => {
    const canvasRef = useRef<HTMLCanvasElement | null>(null);
    let W = window.innerWidth;
    let H = window.innerHeight;
    const maxConfettis = 150;
    const particles: ConfettiParticle[] = [];

    const possibleColors = [
        'DodgerBlue',
        'OliveDrab',
        'Gold',
        'Pink',
        'SlateBlue',
        'LightBlue',
        'Gold',
        'Violet',
        'PaleGreen',
        'SteelBlue',
        'SandyBrown',
        'Chocolate',
        'Crimson',
    ];

    interface ConfettiParticle {
        x: number;
        y: number;
        r: number;
        d: number;
        color: string;
        tilt: number;
        tiltAngleIncremental: number;
        tiltAngle: number;
        draw: () => void;
    }

    const randomFromTo = (from: number, to: number): number =>
        Math.floor(Math.random() * (to - from + 1) + from);

    const confettiParticle = (): ConfettiParticle => ({
        x: Math.random() * W,
        y: Math.random() * H,
        r: randomFromTo(11, 33),
        d: Math.random() * maxConfettis + 11,
        color: possibleColors[Math.floor(Math.random() * possibleColors.length)],
        tilt: Math.floor(Math.random() * 33) - 11,
        tiltAngleIncremental: Math.random() * 0.07 + 0.05,
        tiltAngle: 0,
        draw: function () {
            if (!canvasRef.current) return;

            const context = canvasRef.current.getContext('2d');
            if (!context) return;

            context.beginPath();
            context.lineWidth = this.r / 2;
            context.strokeStyle = this.color;
            context.moveTo(this.x + this.tilt + this.r / 3, this.y);
            context.lineTo(this.x + this.tilt, this.y + this.tilt + this.r / 5);
            context.stroke();
        },
    });

    const draw = (): void => {
        if (!canvasRef.current) return;

        const context = canvasRef.current.getContext('2d');
        if (!context) return;

        context.clearRect(0, 0, W, H);

        for (let i = 0; i < maxConfettis; i++) {
            particles[i].draw();
        }

        let particle: ConfettiParticle = {} as ConfettiParticle;
        let remainingFlakes = 0;

        for (let i = 0; i < maxConfettis; i++) {
            particle = particles[i];

            particle.tiltAngle += particle.tiltAngleIncremental;
            particle.y += (Math.cos(particle.d) + 3 + particle.r / 2) / 2;
            particle.tilt = Math.sin(particle.tiltAngle - i / 3) * 15;

            if (particle.y <= H) remainingFlakes++;

            if (
                particle.x > W + 30 ||
                particle.x < -30 ||
                particle.y > H
            ) {
                particle.x = Math.random() * W;
                particle.y = -30;
                particle.tilt = Math.floor(Math.random() * 10) - 20;
            }
        }
    };

    useEffect(() => {
        const handleResize = (): void => {
            W = window.innerWidth;
            H = window.innerHeight;
            if (canvasRef.current) {
                canvasRef.current.width = W;
                canvasRef.current.height = H;
            }
        };

        const animationLoop = (): void => {
            draw();
            requestAnimationFrame(animationLoop);
        };

        window.addEventListener('resize', handleResize);

        for (let i = 0; i < maxConfettis; i++) {
            particles.push(confettiParticle());
        }

        // Initialize
        if (canvasRef.current) {
            canvasRef.current.width = W;
            canvasRef.current.height = H;
            animationLoop();
        }

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    return (
        <canvas
            ref={canvasRef}
        ></canvas>
    );
};

export default Confetti;
