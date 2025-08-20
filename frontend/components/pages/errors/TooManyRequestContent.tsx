"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Zap, Clock, Gauge, ArrowLeft, HomeIcon, Coffee } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const TooManyRequestContent = () => {
    const [speedState, setSpeedState] = useState<number>(0);
    const [cooldown, setCooldown] = useState<number>(60);
    const [requestCount, setRequestCount] = useState<number>(999);

    useEffect(() => {
        console.error("429 Error: Muitas requisições - Rate limit exceeded");

        const stateInterval = setInterval(() => {
            setSpeedState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 1200);

        const cooldownInterval = setInterval(() => {
            setCooldown((prev) => {
                if (prev <= 1) return 60;
                return prev - 1;
            });
        }, 1000);

        const requestInterval = setInterval(() => {
            setRequestCount((prev) => prev + Math.floor(Math.random() * 5));
        }, 800);

        return () => {
            clearInterval(stateInterval);
            clearInterval(cooldownInterval);
            clearInterval(requestInterval);
        };
    }, []);

    const getSpeedIcon = () => {
        switch (speedState) {
            case 0:
                return <Zap className="h-20 w-20 text-yellow-500 animate-bounce" />;
            case 1:
                return <Gauge className="h-20 w-20 text-red-500 animate-pulse" />;
            default:
                return <Clock className="h-20 w-20 text-orange-500 animate-spin" />;
        }
    };

    const getSpeedometer = () => {
        const percentage = Math.min((requestCount / 1000) * 100, 100);
        return (
            <div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3">
                <div
                    className="bg-gradient-to-r from-yellow-400 via-red-500 to-red-600 h-3 rounded-full transition-all duration-300"
                    style={{ width: `${percentage}%` }}
                />
            </div>
        );
    };

    return (
        <div className="h-screen flex flex-col items-center justify-center bg-gradient-to-b from-yellow-50 to-red-100 dark:from-gray-900 dark:to-red-900/20 p-4">
            <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-red-200 dark:border-red-700">
                <div className="flex flex-col items-center text-center">
                    <div className="mb-4">
                        {getSpeedIcon()}
                    </div>

                    <h1 className="text-6xl font-bold mb-2 text-red-600 dark:text-red-400">429</h1>
                    <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                        Calma aí, Flash! ⚡
                    </h2>

                    <p className="text-gray-600 dark:text-gray-400 mb-6">
                        Uau! Você está mais rápido que um programador buscando Stack Overflow!
                        Dê uma respirada, o servidor precisa de uma pausa. 🏃‍♂️💨
                    </p>

                    <div className="w-full mb-6 p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-700">
                        <p className="text-sm text-red-700 dark:text-red-300 mb-3">
                            🚦 Medidor de velocidade:
                        </p>
                        <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded space-y-3">
                            <div className="flex items-center justify-between text-xs">
                                <span>Requisições:</span>
                                <span className="text-red-500 font-bold">{requestCount}/min</span>
                            </div>
                            {getSpeedometer()}
                            <div className="flex items-center justify-between text-xs">
                                <span>Cooldown:</span>
                                <span className="text-orange-500 animate-pulse">{cooldown}s</span>
                            </div>
                            <div className="text-center text-xs text-red-500 border-t pt-2">
                                🚨 LIMITE EXCEDIDO - Reduza a velocidade!
                            </div>
                        </div>
                    </div>

                    <div className="space-y-4 w-full">
                        <div className="p-4 bg-orange-50 dark:bg-orange-900/20 rounded-lg">
                            <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                🐌 Como desacelerar:
                            </p>
                            <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                <div className="flex items-center">
                                    <span className="mr-2">☕</span>
                                    Faça uma pausa para o café
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">🧘</span>
                                    Pratique mindfulness digital
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">⏰</span>
                                    Espere alguns minutos
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">🐢</span>
                                    Seja mais como uma tartaruga
                                </div>
                            </div>
                        </div>

                        <div className="flex flex-col sm:flex-row gap-3 justify-center">
                            <Button
                                onClick={() => window.history.back()}
                                variant="outline"
                                className="flex-1"
                            >
                                <ArrowLeft className="h-4 w-4 mr-2" />
                                Voltar
                            </Button>
                            <Button
                                variant="secondary"
                                className="flex-1"
                                disabled
                            >
                                <Coffee className="h-4 w-4 mr-2" />
                                Pausa ({cooldown}s)
                            </Button>
                            <Link href="/" className="flex-1">
                                <Button className="w-full bg-red-500 hover:bg-red-600">
                                    <HomeIcon className="h-4 w-4 mr-2" />
                                    Início (devagar)
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </Card>

            <div className="mt-8 text-center">
                <p className="text-gray-600 dark:text-gray-400 text-sm">
                    ⏳ Paciência é uma virtude... principalmente na programação! 😌
                </p>
            </div>
        </div>
    );
};

export default TooManyRequestContent;